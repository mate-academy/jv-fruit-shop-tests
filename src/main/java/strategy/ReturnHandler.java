package strategy;

import model.FruitTransaction;

public class ReturnHandler implements TransactionHandler {
    @Override
    public Integer apply(Integer fruit, FruitTransaction transaction) {
        return fruit + transaction.getQuantity();
    }
}
