package core.basesyntax.service.implementations;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.inerfaces.OperationHandler;

public class SupplyOperationHandlerImpl implements OperationHandler {
    private FruitsDao fruitsDao;

    public SupplyOperationHandlerImpl(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void doOperation(Fruit fruit, Integer quantity) {
        fruitsDao.addProduct(fruit, quantity + fruitsDao.getValue(fruit));
    }
}
