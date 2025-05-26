import java.util.*;
import java.util.stream.Collectors;

/** Popularity‑based recommender implementation. */
class PopularityBasedRecommender<T extends Item> extends RecommenderSystem<T> {
    private static final int POPULARITY_THRESHOLD = 100;
    public PopularityBasedRecommender(Map<Integer, User> users,
                                      Map<Integer, T> items,
                                      List<Rating<T>> ratings) {
        super(users, items, ratings);
    }

    @Override
    public List<T> recommendTop10(int userId) {
        return items.values().stream()
                .filter(i -> !isRatedByUser(userId, i.getId())) // Remove items the user already rated
                .filter(i -> getItemRatingsCount(i.getId()) >= POPULARITY_THRESHOLD) // Popular items only
                .sorted(Comparator
                        .comparingDouble((T item) -> getItemAverageRating(item.getId())).reversed() // Higher avg rating first
                        .thenComparing(item -> getItemRatingsCount(item.getId()), Comparator.reverseOrder()) // More ratings
                        .thenComparing(Item::getName)) // Lexicographic order
                .limit(NUM_OF_RECOMMENDATIONS)
                .collect(Collectors.toList());
    }


    public double getItemAverageRating(int itemId) {
        // Returns the average rating of the item with the given ID
        return  ratingsByItem.get(itemId)
                .stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(0.0);

    }

    public int getItemRatingsCount(int itemId) {
        // Returns the number of times the item with the given ID was rated by users
        List<Rating<T>> itemRatings = ratingsByItem.get(itemId);
        return itemRatings == null ? 0 : itemRatings.size();
    }

    public boolean isRatedByUser(int userId, int itemId) {
        return ratingsByUser.get(userId).stream()
                .anyMatch(r -> r.getItemId() == itemId);
    }

}
