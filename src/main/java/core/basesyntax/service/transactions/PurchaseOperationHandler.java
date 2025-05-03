package core.basesyntax.service.transactions;

import core.basesyntax.exceptions.FruitTransactionException;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public int countQuantity(int currentAmount, int operationAmount) {
        if (operationAmount < 0) {
            throw new FruitTransactionException("operation amount can not be less than 0");
        }
        if (currentAmount < 0) {
            throw new FruitTransactionException("current amount can not be less than 0");
        }
        int subtraction = currentAmount - operationAmount;
        if (subtraction < 0) {
            throw new FruitTransactionException("balance can not be less than 0");
        }
        return subtraction;
    }
}
