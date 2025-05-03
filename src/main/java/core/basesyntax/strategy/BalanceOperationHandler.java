package core.basesyntax.strategy;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private FruitShopDao fruitShopDao;

    public BalanceOperationHandler(FruitShopDao fruitShopDao) {
        this.fruitShopDao = fruitShopDao;
    }

    @Override
    public void handleOperation(FruitTransaction transaction) {
        fruitShopDao.put(transaction.getFruit(), transaction.getQuantity());
    }
}
