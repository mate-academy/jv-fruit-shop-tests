package core.basesyntax.service.handler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public int calculateQuantity(int balance, int quantity) {
        if (balance >= 0 && quantity >= 0) {
            if (isEnough(balance, quantity)) {
                return balance - quantity;
            }
            throw new RuntimeException("Not enough fruits in store");
        }
        throw new RuntimeException("Numbers must be positive numbers");
    }

    private boolean isEnough(int balance, int quantity) {
        return balance >= quantity;
    }
}
