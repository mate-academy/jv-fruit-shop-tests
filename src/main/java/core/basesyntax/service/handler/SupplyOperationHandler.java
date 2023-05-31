package core.basesyntax.service.handler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public int calculateQuantity(int balance, int quantity) {
        if (balance >= 0 && quantity >= 0) {
            return balance + quantity;
        }
        throw new RuntimeException("Numbers must be positive numbers");
    }
}
