package core.basesyntax.strategy.validator;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;

public interface StorageValidator extends OperationValidator {
    default boolean doesStorageContainsFruit(FruitModel fruitModel, String operationName) {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        if (!storageDao.containsKey(fruitModel.getName())) {
            throw new RuntimeException("Operation " + operationName + System.lineSeparator()
                    + "There is no such fruit " + fruitModel.getName());
        }
        return true;
    }
}
