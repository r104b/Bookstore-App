package bookstoreapp;

/**
 * Concrete State: Silver.
 * A customer is in Silver state when they have fewer than 1000 points.
 */
public class SilverState implements CustomerState {

    @Override
    public String getStatusName() {
        return "Silver";
    }

    @Override
    public CustomerState updateState(int points) {
        if (points >= 1000) {
            return new GoldState();
        }
        return this;
    }
}
