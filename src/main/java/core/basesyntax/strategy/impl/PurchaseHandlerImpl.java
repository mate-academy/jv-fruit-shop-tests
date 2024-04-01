package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseHandlerImpl implements OperationHandler {
    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public void apply(FruitTransaction transaction) {
        int quantityBefore = fruitDao.get(transaction.getFruit());
        if (quantityBefore < transaction.getQuantity() || transaction.getQuantity() < 0) {
            throw new RuntimeException("You can't  purchase this quantity of fruits");
        }
        fruitDao.add(transaction.getFruit(), quantityBefore - transaction.getQuantity());
    }
}
