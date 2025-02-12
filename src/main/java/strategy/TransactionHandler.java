package strategy;

import model.FruitTransaction;

public interface TransactionHandler {

    Integer apply(Integer fruit, FruitTransaction transaction);
}
