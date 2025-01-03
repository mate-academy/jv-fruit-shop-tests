package core.basesyntax.exceptions;

public class PurchaseBeyondStockException extends RuntimeException {
    public PurchaseBeyondStockException(String message) {
        super(message);
    }
}
