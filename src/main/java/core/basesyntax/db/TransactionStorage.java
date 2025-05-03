package core.basesyntax.db;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionStorage {
    private static final List<FruitTransaction> storage = new ArrayList<>();

    public void add(FruitTransaction transaction) {
        storage.add(transaction);
    }

    public List<FruitTransaction> getAll() {
        return storage;
    }
}
