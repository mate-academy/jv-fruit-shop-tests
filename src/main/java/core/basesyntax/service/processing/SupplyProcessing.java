package core.basesyntax.service.processing;

import core.basesyntax.dao.FruitsDao;
import java.util.Objects;

public class SupplyProcessing implements OperationProcessing {
    private FruitsDao fruitsDao;

    public SupplyProcessing(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public boolean doAction(String fruit, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Amount can't be less than zero");
        }
        int oldAmount = fruitsDao.get(fruit);
        int newAmount = oldAmount + amount;
        fruitsDao.remove(fruit);
        fruitsDao.add(fruit, newAmount);
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
        SupplyProcessing that = (SupplyProcessing) o;
        return Objects.equals(fruitsDao, that.fruitsDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitsDao);
    }
}
