package service.activities;

public class QuantityVerifierImpl implements service.activities.QuantityVerifier {
    @Override
    public void quantityVerify(int quantity, String fruit) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative for fruit: " + fruit);
        }
    }
}
