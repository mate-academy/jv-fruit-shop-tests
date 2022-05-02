package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import java.util.Objects;

public class BalanceHandler implements OperationHandler {
    private final StorageDao dao;

    public BalanceHandler(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(Fruit fruit, int quantity) {
        dao.add(fruit, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceHandler that = (BalanceHandler) o;
        return Objects.equals(dao, that.dao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dao);
    }
}
