package core.basesyntax.db.impl;

import core.basesyntax.db.DataBase;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;

public class DataBaseImpl implements DataBase {
    private final Map<String, Integer> fruitTransactions = new HashMap<>();

    @Override
    public void addFruitTransaction(FruitTransaction fruitTransaction) {
        fruitTransactions.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }

    @Override
    public Map<String, Integer> getFruitTransactions() {
        return fruitTransactions;
    }
}
