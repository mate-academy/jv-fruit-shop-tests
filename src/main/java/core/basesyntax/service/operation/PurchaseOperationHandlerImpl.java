package core.basesyntax.service.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.OperationException;
import core.basesyntax.model.Fruit;
import java.util.Objects;

public class PurchaseOperationHandlerImpl implements OperationHandler {
    private final StorageDao fruitStorageDao;

    public PurchaseOperationHandlerImpl(StorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(Fruit fruit, int quantity) {
        if (fruitStorageDao.getValue(fruit) - quantity < 0) {
            throw new OperationException("There are no products to purchase");
        }
        fruitStorageDao.subtract(fruit, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseOperationHandlerImpl that = (PurchaseOperationHandlerImpl) o;
        return Objects.equals(fruitStorageDao, that.fruitStorageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStorageDao);
    }
}
