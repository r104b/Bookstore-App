package bookstoreapp;

/**
 * Concrete State: Gold.
 * A customer is in Gold state when they have 1000 or more points.
 */
public class GoldState implements CustomerState {

    @Override
    public String getStatusName() {
        return "Gold";
    }

    @Override
    public CustomerState updateState(int points) {
        if (points < 1000) {
            return new SilverState();
        }
        return this;
    }
}
