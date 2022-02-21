package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public BalanceOperationHandler() {
        this.fruitDao = new FruitDaoImpl();
    }

    @Override
    public void doOperation(String fruitType, String quantity) {
        if (Integer.parseInt(quantity) < 0 || quantity == null || fruitType == null) {
            throw new RuntimeException("Invalid params. Params: quantity = " + quantity
                    + " fruitType = " + fruitType);
        }
        Fruit fruit = new Fruit(fruitType);
        fruitDao.addFruit(fruit);
        int previousQuantity = fruitDao.getQuantity(fruit);
        fruitDao.setQuantity(fruit, previousQuantity + Integer.parseInt(quantity));
    }
}
