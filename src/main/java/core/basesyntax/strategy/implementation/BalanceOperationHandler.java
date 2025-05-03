package core.basesyntax.strategy.implementation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exceptions.ProductAmountException;
import core.basesyntax.strategy.interfaces.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handle(String productName, int amount) {
        if (amount < 0) {
            throw new ProductAmountException(AMOUNT_LESS_THEN_ZERO_EXCEPTION_MESSAGE + productName);
        }
        if (productName == null) {
            throw new NullPointerException("Product can't be null");
        }
        storageDao.addProduct(productName, amount);
    }
}
