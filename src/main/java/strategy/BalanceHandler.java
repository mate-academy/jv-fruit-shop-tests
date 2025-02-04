package strategy;

import model.FruitTransaction;

public class BalanceHandler implements TransactionHandler {
    @Override
    public Integer apply(Integer fruit, FruitTransaction transaction) {
        if ( fruit == null ) {
            return transaction.getQuantity();
        } else {
            return transaction.getQuantity() + fruit;
        }
    }
}
