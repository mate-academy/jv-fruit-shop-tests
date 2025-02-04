package strategy;

import model.FruitTransaction;

public class ReturnHandler implements TransactionHandler {
    @Override
    public Integer apply(Integer fruit, FruitTransaction transaction) {
        if(fruit == null) {
            return transaction.getQuantity();
        } else {
            return fruit + transaction.getQuantity();
        }
    }
}
