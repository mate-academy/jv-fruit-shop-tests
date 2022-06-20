package core.basesyntax.service.processing;

import core.basesyntax.dao.FruitsDao;
import java.util.Objects;

public class BalanceProcessing implements OperationProcessing {
    private FruitsDao fruitsDao;

    public BalanceProcessing(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public boolean doAction(String fruit, int amount) {
        fruitsDao.add(fruit, amount);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceProcessing that = (BalanceProcessing) o;
        return Objects.equals(fruitsDao, that.fruitsDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitsDao);
    }
}
