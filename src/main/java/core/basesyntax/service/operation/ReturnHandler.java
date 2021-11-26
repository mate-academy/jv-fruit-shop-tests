package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.exception.OperationException;
import java.util.Objects;

public class ReturnHandler implements OperationHandler {
    private final FruitStorageDao fruitStorageDao;

    public ReturnHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void apply(String fruitName, int quantity) {
        if (!fruitStorageDao.containsKey(fruitName)) {
            throw new OperationException("Unavailable fruit cannot be returned");
        }
        fruitStorageDao.update(fruitName, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReturnHandler that = (ReturnHandler) o;
        return Objects.equals(fruitStorageDao, that.fruitStorageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStorageDao);
    }
}
