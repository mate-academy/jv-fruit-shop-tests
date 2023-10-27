package core.basesyntax.service.handler.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class PurchaseOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public PurchaseOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void updateStorage(FruitTransaction transaction) {
        validAmountOfFruit(transaction);
        Map.Entry<Fruit, Integer> fruitAndAmount = storageDao.get(transaction.getFruitName());
        Integer fruitAmount = fruitAndAmount.getValue();
        Integer fruitAmountFromTransaction = transaction.getAmount();
        fruitAndAmount.setValue(fruitAmount - fruitAmountFromTransaction);
    }

    private void validAmountOfFruit(FruitTransaction transaction) {
        validAmount(transaction);
        String fruitName = transaction.getFruitName();
        if (!storageDao.isInStorage(fruitName)) {
            throw new RuntimeException("There is no such fruit!!!");
        }
        if (storageDao.get(fruitName).getValue() < transaction.getAmount()) {
            throw new RuntimeException("Amount of " + transaction.getFruitName()
                    + " isn't enough!!!");
        }
    }
}
