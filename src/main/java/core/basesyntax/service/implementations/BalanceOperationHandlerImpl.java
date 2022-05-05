package core.basesyntax.service.implementations;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.inerfaces.OperationHandler;

public class BalanceOperationHandlerImpl implements OperationHandler {
    private FruitsDao fruitsDao;

    public BalanceOperationHandlerImpl(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void doOperation(Fruit fruit, Integer quantity) {
        fruitsDao.addProduct(fruit, quantity);
    }
}
