package core.basesyntax.operations;

import core.basesyntax.dao.DaoFruit;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    private final DaoFruit fruitDao;

    public BalanceOperation(DaoFruit fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        fruitDao.addFruits(fruitTransaction.getFruit(), fruitTransaction.getAmount());
    }
}
