package core.basesyntax.service.transaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public int countQuantity(int currentAmount, int operationAmount) {
        if (operationAmount < 0) {
            throw new RuntimeException("operation amount can not be less than 0");
        }
        if (currentAmount < 0) {
            throw new RuntimeException("current amount can not be less than 0");
        }
        return currentAmount + operationAmount;
    }
}
