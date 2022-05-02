package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.Objects;

public class PurchaseHandlerImpl implements OperationHandler {
    private final StorageDao dao;

    public PurchaseHandlerImpl(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(Fruit fruit, int quantity) {
        if (Storage.storage.get(fruit) - quantity < 0) {
            throw new RuntimeException("There are no products...");
        }
        dao.take(fruit, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseHandlerImpl that = (PurchaseHandlerImpl) o;
        return Objects.equals(dao, that.dao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dao);
    }
}
