package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationException;

public class PurchaseOperationHandlerImpl implements OperationHandler {
    private final FruitStorageDao fruitStorageDao;

    public PurchaseOperationHandlerImpl(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(String fruitName, int quantity) {
        if (fruitStorageDao.getValue(fruitName) - quantity < 0) {
            throw new OperationException("Can not overgo zero balance"
                    + ", please check the quantity");
        }
        fruitStorageDao.update(fruitName, quantity);
    }
}
