package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.exception.OperationException;
import java.util.Objects;

public class PurchaseHandler implements OperationHandler {
    private final FruitStorageDao fruitStorageDao;

    public PurchaseHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(String fruitName, int quantity) {
        if (fruitStorageDao.getValue(fruitName) - quantity < 0) {
            throw new OperationException("You can't purchase more products than are available");
        }
        fruitStorageDao.update(fruitName, -quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseHandler that = (PurchaseHandler) o;
        return Objects.equals(fruitStorageDao, that.fruitStorageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStorageDao);
    }
}
