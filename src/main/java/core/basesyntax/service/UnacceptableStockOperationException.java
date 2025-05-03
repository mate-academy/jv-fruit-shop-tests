package core.basesyntax.service;

public class UnacceptableStockOperationException extends RuntimeException {
    public UnacceptableStockOperationException(String message) {
        super(message);
    }
}
