package core.basesyntax.operation;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandler implements OperationHandler {
    private final FruitsDao fruitsDao;

    public ReturnHandler(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        fruitsDao.addFruit(transaction.getFruit(),
                transaction.getQuantity()
                        + fruitsDao.getQuantityByFruit(transaction.getFruit()));
    }
}
