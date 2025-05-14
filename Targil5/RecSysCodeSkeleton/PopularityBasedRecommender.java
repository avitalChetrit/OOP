import java.util.*;

import static java.util.stream.Collectors.*;

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
        // TODO: implement
        return null;
    }

    public double getItemAverageRating(int itemId) {
        // TODO: implement
        // Returns the average rating of the item with the given ID
        return ratings.stream()
                .filter(r -> r.getItemId() == itemId) // Filters only the ratings for the requested item
                .mapToDouble(Rating::getRating) // Converts the ratings to numeric (double) values
                .average() // Computes the average
                .orElse(0.0); // Returns 0 if there are no ratings
    }
    public int getItemRatingsCount(int itemId) {
        // TODO: implement
        // Returns the number of times the item with the given ID was rated by users
        return (int) ratings.stream()
                .filter(r -> r.getItemId() == itemId) // Filters only the ratings for the requested item
                .count(); // Counts how many such ratings exist

    }

}
