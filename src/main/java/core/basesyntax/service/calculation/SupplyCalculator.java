package core.basesyntax.service.calculation;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;

public class SupplyCalculator implements TransactionCalculation {
    private final ShopDao shopDao = new ShopDaoImpl();

    @Override
    public void calculate(FruitTransaction fruitTransaction) {
        shopDao.add(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity() + Storage.storage.get(fruitTransaction.getFruit()));
    }
}
