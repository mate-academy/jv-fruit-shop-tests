package core.basesyntax.java.core.basesyntax.strategy.handler;

public class PurchaseHandler implements OperationHandler {
    @Override
    public Integer getOperationHandler(int quantity, int value) {
        return quantity - value;
    }
}
