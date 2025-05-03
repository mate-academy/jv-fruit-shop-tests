package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;
    private StorageDao storageDao;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
        storageDao = new StorageDaoImpl();
    }

    @Override
    public Map<String, Integer> process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions cannot be null");
        }

        for (FruitTransaction transaction : transactions) {
            operationStrategy.get(transaction.getType()).performOperation(transaction);
        }
        return storageDao.getAll();
    }
}
