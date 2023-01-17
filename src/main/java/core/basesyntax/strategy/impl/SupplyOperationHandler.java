package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    {
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public void handle(FruitTransaction transaction) {
        if (!fruitDao.containsFruit(transaction.getFruit())) {
            fruitDao.updateQuantity(transaction.getFruit(), transaction.getQuantity());
            return;
        }
        Integer currentQuantity = fruitDao.getQuantity(transaction.getFruit());
        Integer newQuantity = currentQuantity + transaction.getQuantity();
        fruitDao.updateQuantity(transaction.getFruit(), newQuantity);
    }
}
