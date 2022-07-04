package core.basesyntax.service.calculation;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;

public class PurchaseCalculator implements TransactionCalculation {
    private final ShopDao shopDao = new ShopDaoImpl();

    @Override
    public void calculate(FruitTransaction fruitTransaction) {
        if (Storage.storage.get(fruitTransaction.getFruit()) - fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Purchase can't be "
                    + "greater than the sum of the balance and supply");
        } else {
            shopDao.add(fruitTransaction.getFruit(),
                    Storage.storage.get(fruitTransaction.getFruit())
                            - fruitTransaction.getQuantity());
        }
    }
}
