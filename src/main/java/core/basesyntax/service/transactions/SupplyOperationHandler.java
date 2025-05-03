package core.basesyntax.service.transactions;

import core.basesyntax.exceptions.FruitTransactionException;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public int countQuantity(int currentAmount, int operationAmount) {
        if (operationAmount < 0) {
            throw new FruitTransactionException("operation amount can not be less than 0");
        }
        if (currentAmount < 0) {
            throw new FruitTransactionException("current amount can not be less than 0");
        }
        int sum = currentAmount + operationAmount;
        return sum;
    }
}
