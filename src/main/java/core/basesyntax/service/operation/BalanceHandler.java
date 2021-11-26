package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;

import java.util.Objects;

public class BalanceHandler implements OperationHandler {
    private final FruitStorageDao fruitStorageDao;

    public BalanceHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(String fruitName, int quantity) {
        fruitStorageDao.add(fruitName, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceHandler that = (BalanceHandler) o;
        return Objects.equals(fruitStorageDao, that.fruitStorageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStorageDao);
    }
}
