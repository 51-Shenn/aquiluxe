package datamodels;

public class CarDiscount extends VehicleDiscount {

    private final double CAR_DISCOUNT_RATE = 0.10;
    
    @Override
    public double calculateDiscountPrice(double price) {
        return price * CAR_DISCOUNT_RATE;
    }

    @Override
    public String toString() {
        return String.format("Car Discount: %.2f%% Off", CAR_DISCOUNT_RATE);
    }
}
