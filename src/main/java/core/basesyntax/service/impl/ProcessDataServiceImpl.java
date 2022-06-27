package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ProcessDataService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ProcessDataServiceImpl implements ProcessDataService {
    @Override
    public List<Fruit> processData(List<Fruit> parsedValues) {
        StorageDao storageDao = new StorageDaoImpl();
        for (Fruit fruit : parsedValues) {
            Fruit f = storageDao.getFruit(fruit);
            if (f == null) {
                storageDao.addFruit(fruit);
            } else {
                fruit.setQuantity(OperationStrategy
                        .getOperationServiceStrategy(fruit.getOperation())
                        .getActionByOperation(fruit.getQuantity()).applyAsInt(f.getQuantity()));
                storageDao.changeQuantityOfFruit(fruit);
            }
        }
        return Storage.storage;
    }
}
