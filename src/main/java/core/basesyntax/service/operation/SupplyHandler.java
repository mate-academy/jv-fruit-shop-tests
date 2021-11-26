package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;

import java.util.Objects;

public class SupplyHandler implements OperationHandler {
    private final FruitStorageDao fruitStorageDao;

    public SupplyHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(String fruitName, int quantity) {
        if (fruitStorageDao.containsKey(fruitName)) {
            fruitStorageDao.update(fruitName, quantity);
        } else {
            fruitStorageDao.add(fruitName, quantity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplyHandler that = (SupplyHandler) o;
        return Objects.equals(fruitStorageDao, that.fruitStorageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStorageDao);
    }
}
