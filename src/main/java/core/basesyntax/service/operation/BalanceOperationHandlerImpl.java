package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandlerImpl implements OperationHandler {
    private final FruitShopDao fruitShopDao;

    public BalanceOperationHandlerImpl(FruitShopDao fruitShopDao) {
        this.fruitShopDao = fruitShopDao;
    }

    @Override
    public void operation(FruitTransaction fruitTransaction) {
        fruitShopDao.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
