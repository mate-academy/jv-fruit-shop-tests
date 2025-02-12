package strategy;

import model.FruitTransaction;

public class SupplyHandler implements TransactionHandler {
    
    @Override
    public Integer apply(Integer fruit, FruitTransaction transaction) {
        return fruit + transaction.getQuantity();
    }
}
