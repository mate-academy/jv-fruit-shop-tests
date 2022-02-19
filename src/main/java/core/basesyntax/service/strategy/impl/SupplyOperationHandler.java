package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public SupplyOperationHandler() {
        this.fruitDao = new FruitDaoImpl();
    }

    @Override
    public void doOperation(String fruitType, String quantity) {
        Fruit fruit = new Fruit(fruitType);
        int previousQuantity = fruitDao.getQuantity(fruit);
        fruitDao.setQuantity(fruit, previousQuantity + Integer.parseInt(quantity));
    }
}
