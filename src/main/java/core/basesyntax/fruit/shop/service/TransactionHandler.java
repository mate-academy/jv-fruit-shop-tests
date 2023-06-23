package core.basesyntax.fruit.shop.service;

import core.basesyntax.fruit.shop.model.FruitTransaction;

import java.util.List;

public interface TransactionHandler {
    void parseStorage(List<FruitTransaction> transactions);
}
