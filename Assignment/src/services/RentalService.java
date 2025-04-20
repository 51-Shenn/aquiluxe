package services;

public class RentalService {
    // updateRentalStatus ( rental : Rental, status : RentalStatus )
    // updatePaymentStatus ( rental : Rental, status : PaymentStatus )
    // cancelRental ( rental : Rental )
    // extendRental ( rental : Rental, newEndDate : LocalDate )
    // approveRentalRequest ( rental Rental : status : RentalStatus ) (admin)
    // returnRentals ( rental : Rental, status : RentalStatus ) (admin)
    // getRentalHistory ( customer : Customer ) # maybe overload

    /*
     * Calculation Methods:
     * calculateBaseRentalCost : daysbetween * rentalpriceday
     * calculateBookingFee : 10% of base rental cost
     * calculateDepositCost : 20% of base rental cost
     * calculateInsuranceCost : 5% of base rental cost
     * calculateTaxCost : 5% of base rental cost
     * calculateTotalRentalCost : base rental cost + booking fee + deposit cost +
     * insurance cost + tax cost
     */

    // logical error validation for input fields
    // billing info
    // rental details : startDate and endDate, pickup and dropoff time, etc.
    // payment method
}
