package core.basesyntax.dao.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class FruitDaoImpl {
    public List<FruitTransaction> getAll() {
        return Storage.transactions;
    }

    public void add(FruitTransaction transaction) {
        Storage.transactions.add(transaction);
    }
}
