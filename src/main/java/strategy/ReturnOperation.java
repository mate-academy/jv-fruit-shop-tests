package strategy;

import database.Storage;
import model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void updateDatabase(FruitTransaction transaction) {
        int quantity = getQuantityToCalculate(transaction);
        String fruit = transaction.getFruit();
        ensureNonNegativeBalance(fruit, quantity);
        Storage.updateStorage(fruit, quantity);
    }

    private int getQuantityToCalculate(FruitTransaction fruitTransaction) {
        return fruitTransaction.getQuantity();
    }

    private void ensureNonNegativeBalance(String fruit, int quantity) {
        if (Storage.getAssortment().containsKey(fruit)) {
            int currentBalanceForThisFruit = Storage.getAssortment().get(fruit);
            if (currentBalanceForThisFruit + quantity < 0) {
                throw new RuntimeException("Negative balance for fruit: " + fruit
                        + " with quantity: " + quantity);
            }
        }
    }
}
