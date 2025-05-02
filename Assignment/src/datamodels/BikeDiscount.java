package datamodels;

public class BikeDiscount extends VehicleDiscount {

    private final double BIKE_DISCOUNT_RATE = 0.10;
    
    @Override
    public double calculateDiscountPrice(double price) {
        return price * BIKE_DISCOUNT_RATE;
    }

    @Override
    public String toString() {
        return String.format("Bike Discount: %.2f%% Off", BIKE_DISCOUNT_RATE);
    }
}
