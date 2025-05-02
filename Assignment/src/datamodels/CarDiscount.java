package datamodels;

public class CarDiscount extends VehicleDiscount {

    private static final double CAR_DISCOUNT_RATE = 0.15;

    public static double getCarDiscountRate() {
        return CAR_DISCOUNT_RATE;
    }

    @Override
    public double calculateDiscountPrice(double price) {
        return price * CAR_DISCOUNT_RATE;
    }

    @Override
    public String toString() {
        return String.format("Car Discount: %.2f%% Off", CAR_DISCOUNT_RATE);
    }
}
