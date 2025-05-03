package core.basesyntax.service.operations;

import core.basesyntax.db.ShopDao;
import core.basesyntax.db.ShopDaoImpl;
import core.basesyntax.service.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    private static final int PURCHASE_MULTIPLIER = -1;
    private final ShopDao shopDao = new ShopDaoImpl();

    @Override
    public void processTransaction(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int quantity = fruitTransaction.getQuantity() * PURCHASE_MULTIPLIER;
        shopDao.purchaseFruit(fruit, quantity);
    }
}
