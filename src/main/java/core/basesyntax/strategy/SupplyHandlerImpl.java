package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.Objects;

public class SupplyHandlerImpl implements OperationHandler {
    private final StorageDao dao;

    public SupplyHandlerImpl(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(Fruit fruit, int quantity) {
        if (!Storage.storage.containsKey(fruit)) {
            dao.add(fruit, quantity);
        } else {
            dao.supply(fruit, quantity);
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
        SupplyHandlerImpl that = (SupplyHandlerImpl) o;
        return Objects.equals(dao, that.dao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dao);
    }
}
