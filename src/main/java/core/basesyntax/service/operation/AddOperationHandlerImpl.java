package core.basesyntax.service.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import java.util.Objects;

public class AddOperationHandlerImpl implements OperationHandler {
    private final StorageDao fruitStorageDao;

    public AddOperationHandlerImpl(StorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(Fruit fruit, int quantity) {
        if (fruitStorageDao.containsKey(fruit)) {
            fruitStorageDao.supply(fruit, quantity);
        } else {
            fruitStorageDao.add(fruit, quantity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddOperationHandlerImpl that = (AddOperationHandlerImpl) o;
        return Objects.equals(fruitStorageDao, that.fruitStorageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStorageDao);
    }
}
