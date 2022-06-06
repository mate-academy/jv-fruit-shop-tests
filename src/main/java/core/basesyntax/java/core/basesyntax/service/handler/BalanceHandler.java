package core.basesyntax.java.core.basesyntax.service.handler;

public class BalanceHandler implements OperationHandler {
    @Override
    public Integer getOperationHandler(int quantity, int value) {
        return quantity + value;
    }
}
