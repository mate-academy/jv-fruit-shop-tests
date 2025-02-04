package strategy;

import model.FruitTransaction;

public class PurchaseHandler implements TransactionHandler {
    @Override
    public Integer apply(Integer fruit, FruitTransaction transaction) {
        if (fruit == null) {
            return 0;
        } else {
            return fruit - transaction.getQuantity();
        }
    }
}
