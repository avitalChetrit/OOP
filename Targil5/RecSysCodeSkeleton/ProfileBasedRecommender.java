import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

/** Profile‑based recommender implementation. */
class ProfileBasedRecommender<T extends Item> extends RecommenderSystem<T> {
    public ProfileBasedRecommender(Map<Integer, User> users,
                                   Map<Integer, T> items,
                                   List<Rating<T>> ratings) {
        super(users, items, ratings);
    }

    @Override
    public List<T> recommendTop10(int userId) {
        User user = users.get(userId);
        if (user == null) return List.of();

        // Step 1: Find matching users
        List<User> matchingUsers = getMatchingProfileUsers(userId);
        Set<Integer> matchingUserIds = matchingUsers.stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        // Step 2: Group matching users' ratings by itemId
        Map<Integer, List<Rating<T>>> muRatingsByItem = ratings.stream()
                .filter(r -> matchingUserIds.contains(r.getUserId()))
                .collect(Collectors.groupingBy(Rating::getItemId));

        // Step 3: Compute average ratings for items with at least 5 ratings from matching users
        Map<Integer, Double> avgRatings = muRatingsByItem.entrySet().stream()
                .filter(e -> e.getValue().size() >= 5)
                .collect(Collectors.toMap( // Convert each entry to (itemId -> average rating) pair
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToDouble(Rating::getRating) // Convert Rating<T> to primitive double
                                .average().orElse(0.0)
                ));

        // Step 4: Build recommendation list
        return items.values().stream()
                // Exclude items already rated by the user
                .filter(item -> ratingsByUser.getOrDefault(userId, List.of()).stream()
                        .noneMatch(r -> r.getItemId() == item.getId()))
                // Only include items that passed the previous step
                .filter(item -> avgRatings.containsKey(item.getId()))
                // Sort based on avg rating desc, then rating count desc, then name
                .sorted(Comparator
                        .comparingDouble((T item) -> avgRatings.get(item.getId())).reversed()
                        .thenComparing(item -> muRatingsByItem.get(item.getId()).size(), Comparator.reverseOrder())
                        .thenComparing(Item::getName)
                )
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<User> getMatchingProfileUsers(int userId) {
        User user = users.get(userId);

        return users.values().stream()
                .filter(u ->
                        Objects.equals(u.getGender(), user.getGender()) &&
                        u.getAge() >= user.getAge() - 5 &&
                        u.getAge() <= user.getAge() + 5 &&
                        u.getId() != userId)
                .collect(Collectors.toList());
    }

}
