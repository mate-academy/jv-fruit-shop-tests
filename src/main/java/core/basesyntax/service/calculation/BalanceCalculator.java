package core.basesyntax.service.calculation;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class BalanceCalculator implements TransactionCalculation {
    private final ShopDao shopDao = new ShopDaoImpl();

    @Override
    public void calculate(FruitTransaction fruitTransaction) {
        shopDao.add(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
