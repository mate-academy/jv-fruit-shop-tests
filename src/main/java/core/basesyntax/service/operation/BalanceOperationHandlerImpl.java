package core.basesyntax.service.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import java.util.Objects;

public class BalanceOperationHandlerImpl implements OperationHandler {
    private final StorageDao fruitStorageDao;

    public BalanceOperationHandlerImpl(StorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(Fruit fruit, int quantity) {
        fruitStorageDao.add(fruit, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceOperationHandlerImpl that = (BalanceOperationHandlerImpl) o;
        return Objects.equals(fruitStorageDao, that.fruitStorageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStorageDao);
    }
}
