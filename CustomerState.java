package bookstoreapp;

/**
 * State interface for the State Design Pattern.
 * Represents the state (Silver or Gold) of a Customer.
 */
public interface CustomerState {
    String getStatusName();
    /**
     * Called after a purchase. Returns the new state based on updated points.
     */
    CustomerState updateState(int points);
}
