package bookstoreapp;

/**
 *  We are using the State Design Pattern to track Silver/Gold status.
 */
public class Customer {
    private String username;
    private String password;
    private int points;
    private CustomerState state;

    public Customer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
        // this one sets the initial state based on points
        this.state = (points >= 1000) ? new GoldState() : new SilverState();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }
    public String getStatus() { return state.getStatusName(); }

    /**
     * This is Called after a purchase (Buy). Adds earned points and transitions state if needed.
     * @param amountSpent the total CAD spent
     */
    public void addPoints(double amountSpent) {
        int earned = (int)(amountSpent * 10);
        this.points += earned;
        this.state = state.updateState(this.points);
    }

    /**
     * Called for [Redeem points and Buy].
     * Redeems as many points as possible (100 pts = 1 CAD discount), cost cannot go below 0.
     * Then adds new points earned on the actual amount paid.
     * @param totalCost the cost before redemption
     * @return the actual cost after redemption (>= 0)
     */
    public double redeemAndBuy(double totalCost) {
        // Only use full 100-point chunks
int pointsUsed = (this.points / 100) * 100;

// Convert to discount
double discount = pointsUsed / 100.0;

// Don't exceed total cost
if (discount > totalCost) {
    discount = totalCost;
    pointsUsed = (int)(totalCost * 100);
}

// Deduct used points
this.points -= pointsUsed;

        double amountPaid = totalCost - discount;
        if (amountPaid < 0) amountPaid = 0;

        // Earn points on amount actually paid
        int earned = (int)(amountPaid * 10);
        this.points += earned;

        // Update state
        this.state = state.updateState(this.points);

        return amountPaid;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + points;
    }
}
