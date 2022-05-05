package core.basesyntax.service.implementations;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.inerfaces.OperationHandler;

public class PurchaseOperationHandlerImpl implements OperationHandler {
    private FruitsDao fruitsDao;

    public PurchaseOperationHandlerImpl(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void doOperation(Fruit fruit, Integer quantity) {
        fruitsDao.addProduct(fruit, fruitsDao.getValue(fruit) - quantity);
    }
}
