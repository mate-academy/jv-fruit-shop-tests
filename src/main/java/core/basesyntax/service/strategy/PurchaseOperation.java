package core.basesyntax.service.strategy;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.exception.NotEnoughProductsException;
import core.basesyntax.model.FruitTransaction;
import java.util.NoSuchElementException;

public class PurchaseOperation implements OperationHandler {
    private FruitStorageDao storageDao;

    public PurchaseOperation(FruitStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public int handle(FruitTransaction transaction) {
        if (!storageDao.getAllFruits().containsKey(transaction.getFruit())) {
            throw new NoSuchElementException(transaction.getFruit() + " is out of stock");
        }
        int currentFruitQuantity = storageDao.getFruitQuantity(transaction.getFruit());
        int purchaseResult = currentFruitQuantity - transaction.getQuantity();
        if (purchaseResult < 0) {
            throw new NotEnoughProductsException("Not enough fruits: "
                        + transaction.getFruit()
                        + ": "
                        + transaction.getQuantity()
                        + " available: "
                        + currentFruitQuantity);
        }
        return storageDao.add(transaction.getFruit(), purchaseResult);
    }
}
