package fruitshop.service;

import fruitshop.model.FruitTransaction;

public interface TransactionProcessor {
    void process(FruitTransaction fruitTransaction);
}
