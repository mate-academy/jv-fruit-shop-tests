package core.basesyntax.dao;

import core.basesyntax.FruitTransactionException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionValidation;
import java.util.Map;

public class StorageDaoImpl implements StorageDao {
    private final FruitTransactionValidation transactionValidation;

    public StorageDaoImpl(FruitTransactionValidation transactionValidation) {
        this.transactionValidation = transactionValidation;
    }

    @Override
    public void add(FruitTransaction transaction) {
        transactionValidation.validateTransaction(transaction);
        Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
    }

    @Override
    public Integer get(FruitTransaction transaction) {
        transactionValidation.validateTransaction(transaction);
        String key = transaction.getFruit();
        if (Storage.storage.containsKey(key)) {
            return Storage.storage.get(key);
        }
        throw new FruitTransactionException("No such fruit in the storage");
    }

    @Override
    public Map<String, Integer> getStorage() {
        return Storage.storage;
    }
}
