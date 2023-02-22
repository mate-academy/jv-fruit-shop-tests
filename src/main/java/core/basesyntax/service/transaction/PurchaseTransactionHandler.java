package core.basesyntax.service.transaction;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Transaction;

public class PurchaseTransactionHandler implements TransactionHandler {
    private StorageDao storageDao;

    public PurchaseTransactionHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handle(Transaction transaction) {
        int difference = isEnoughFruit(transaction);
        storageDao.updateStorage(transaction.getFruitName(), difference);
    }

    private int isEnoughFruit(Transaction transaction) {
        if (!storageDao.getFruitsFromStorage().containsKey(transaction.getFruitName())) {
            throw new RuntimeException(transaction.getFruitName() + " doesn't exist at the storage");
        }
        Integer currentQuantity = storageDao.getFruitsFromStorage()
                .get(transaction.getFruitName());
        int difference = currentQuantity - transaction.getQuantity();
        if (difference < 0) {
            throw new RuntimeException(transaction.getFruitName() + " not enough at the storage");
        }
        return difference;
    }
}
