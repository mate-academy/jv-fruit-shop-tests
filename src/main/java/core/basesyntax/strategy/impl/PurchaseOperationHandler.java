package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.NotEnoughProductAmountException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Objects;

public class PurchaseOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public PurchaseOperationHandler(StorageDao storageDao) {
        Objects.requireNonNull(storageDao);
        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        Objects.requireNonNull(transaction);
        int currentQuantity = storageDao.getAmountByProductName(transaction.fruit());
        if (currentQuantity < transaction.quantity()) {
            throw new NotEnoughProductAmountException(
                    String.format("No enough product %s amount: available - %d, needed - %d",
                            transaction.fruit(), currentQuantity, transaction.quantity())
            );
        }
        storageDao.putProduct(transaction.fruit(), currentQuantity - transaction.quantity());
    }
}
