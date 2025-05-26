import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/** Similarity‑based recommender with bias correction. */
class SimilarityBasedRecommender<T extends Item> extends RecommenderSystem<T> {
    private final double globalBias;
    private final Map<Integer, Double> itemBiases;
    private final Map<Integer, Double> userBiases;

    public SimilarityBasedRecommender(Map<Integer, User> users,
                                      Map<Integer, T> items,
                                      List<Rating<T>> ratings) {
        super(users, items, ratings);

        // 1. Compute global bias (average of all ratings)
        this.globalBias = ratings.stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(0.0);

        // 2. Compute item biases (average of rating - global bias per item)
        this.itemBiases = ratings.stream()
                .collect(groupingBy(Rating::getItemId,
                        averagingDouble(r -> r.getRating() - globalBias)));

        // 3. Compute user biases (average of rating - global bias - item bias per user)
        this.userBiases = ratings.stream()
                .collect(groupingBy(Rating::getUserId,
                        averagingDouble(r -> r.getRating()
                                - globalBias
                                - itemBiases.getOrDefault(r.getItemId(), 0.0))));
    }

    public double getBiasFreeRating(Rating<T> r) {
        double itemBias = itemBiases.getOrDefault(r.getItemId(), 0.0);
        double userBias = userBiases.getOrDefault(r.getUserId(), 0.0);
        return r.getRating() - globalBias - itemBias - userBias;
    }

    @Override
    public List<T> recommendTop10(int userId) {
        // 1. Get bias-free ratings for current user
        Set<Integer> ratedItemIds = ratings.stream()
                .filter(r -> r.getUserId() == userId)
                .map(Rating::getItemId)
                .collect(toSet());

        // 2. Compute similarity for all other users
        Map<Integer, Double> similarities = users.keySet().stream()
                .filter(uid -> uid != userId)
                .collect(toMap(
                        Function.identity(),
                        uid -> getSimilarity(userId, uid)
                ));

        // 3. Get top 10 most similar users (with similarity > 0)
        List<Integer> topSimilarUsers = similarities.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 4. Collect candidate items (rated by at least 5 similar users and not rated by userId)
        Map<Integer, List<Rating<T>>> itemRatingsBySimilarUsers = ratings.stream()
                .filter(r -> topSimilarUsers.contains(r.getUserId()))
                .filter(r -> !ratedItemIds.contains(r.getItemId()))
                .collect(groupingBy(Rating::getItemId));

        Map<Integer, Double> predictedRatings = itemRatingsBySimilarUsers.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 5) // At least five ratings
                .map(entry -> {
                    int itemId = entry.getKey();
                    List<Rating<T>> itemRatings = entry.getValue();

                    double[] numeratorAndDenominator = itemRatings.stream()
                            .map(r -> {
                                double similarity = similarities.getOrDefault(r.getUserId(), 0.0);
                                double biasFree = getBiasFreeRating(r);
                                return new double[]{similarity * biasFree, Math.abs(similarity)};
                            })
                            .reduce(new double[]{0.0, 0.0}, (a, b) -> new double[]{
                                    a[0] + b[0], // sum numerator
                                    a[1] + b[1]  // sum denominator
                            });

                    double denominator = numeratorAndDenominator[1];
                    if (denominator == 0) return null;

                    double predictedBiasFree = numeratorAndDenominator[0] / denominator;
                    double predicted = predictedBiasFree
                            + globalBias
                            + itemBiases.getOrDefault(itemId, 0.0)
                            + userBiases.getOrDefault(userId, 0.0);

                    return Map.entry(itemId, predicted);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        // 5. Return top-10 recommended items (with tie-breaking)
        return predictedRatings.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed()
                        .thenComparing(e -> getItemRatingsCount(e.getKey()), Comparator.reverseOrder())
                        .thenComparing(e -> items.get(e.getKey()).getName()))
                .limit(10)
                .map(e -> items.get(e.getKey()))
                .collect(toList());
    }

    private long getItemRatingsCount(int itemId) {
        return ratings.stream()
                .filter(r -> r.getItemId() == itemId)
                .count();
    }



    /** Dot‑product similarity; 0 if <10 shared items. */
    public double getSimilarity(int u1, int u2) {
        // מוציאים את כל הדירוגים של u1 ו־u2 לתוך מפות: itemId -> דירוג נטול הטיות
        Map<Integer, Double> u1Ratings = ratings.stream()
                .filter(r -> r.getUserId() == u1)
                .collect(toMap(r -> r.getItemId(), this::getBiasFreeRating));

        Map<Integer, Double> u2Ratings = ratings.stream()
                .filter(r -> r.getUserId() == u2)
                .collect(toMap(r -> r.getItemId(), this::getBiasFreeRating));

        // מזהים את הפריטים שדורגו על ידי שני המשתמשים
        Set<Integer> commonItems = u1Ratings.keySet().stream()
                .filter(u2Ratings::containsKey)
                .collect(toSet());

        // אם יש פחות מ־10 פריטים משותפים, הדמיון הוא 0
        if (commonItems.size() < 10) return 0;

        // חישוב מכפלה סקלרית של הדירוגים נטולי ההטיות
        return commonItems.stream()
                .mapToDouble(itemId -> u1Ratings.get(itemId) * u2Ratings.get(itemId))
                .sum();
    }

    public double getGlobalBias() {
        return globalBias;
    }
    public double getItemBias(int itemId) {
        return itemBiases.getOrDefault(itemId, 0.0);
    }
    public double getUserBias(int userId) {
        return userBiases.getOrDefault(userId, 0.0);
    }
    public void printGlobalBias() {
        System.out.println("Global bias: " + String.format("%.2f", globalBias));
    }

    public void printItemBias(int itemId) {
        System.out.println("Item bias for item " + itemId + ": " +
                String.format("%.2f", itemBiases.getOrDefault(itemId, 0.0)));
    }

    public void printUserBias(int userId) {
        System.out.println("User bias for user " + userId + ": " +
                String.format("%.2f", userBiases.getOrDefault(userId, 0.0)));
    }
}
