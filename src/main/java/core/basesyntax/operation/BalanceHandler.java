package core.basesyntax.operation;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    private final FruitsDao fruitsDao;

    public BalanceHandler(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        fruitsDao.addFruit(transaction.getFruit(), transaction.getQuantity());
    }
}
