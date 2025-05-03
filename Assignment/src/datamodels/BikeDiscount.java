package datamodels;

public class BikeDiscount extends VehicleDiscount {

    private static final double BIKE_DISCOUNT_RATE = 0.10;

    public static double getBikeDiscountRate() {
        return BIKE_DISCOUNT_RATE;
    }
    
    @Override
    public double calculateDiscountPrice(double price) {
        return price * BIKE_DISCOUNT_RATE;
    }

    @Override
    public String toString() {
        return String.format("Bike Discount: %.2f%% Off", BIKE_DISCOUNT_RATE);
    }
}
