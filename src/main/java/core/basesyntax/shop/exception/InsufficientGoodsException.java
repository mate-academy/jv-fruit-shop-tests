package core.basesyntax.shop.exception;

public class InsufficientGoodsException extends Exception {
    public InsufficientGoodsException(String errorMessage) {
        super(errorMessage);
    }
}
