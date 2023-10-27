package core.basesyntax.service.handler.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class ReturnOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public ReturnOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void updateStorage(FruitTransaction transaction) {
        validAmount(transaction);
        String fruitName = transaction.getFruitName();
        if (!storageDao.isInStorage(fruitName)) {
            storageDao.add(new Fruit(fruitName), transaction.getAmount());
        } else {
            Map.Entry<Fruit, Integer> fruitAndAmount = storageDao.get(fruitName);
            Integer fruitAmount = fruitAndAmount.getValue();
            Integer fruitAmountFromTransaction = transaction.getAmount();
            fruitAndAmount.setValue(fruitAmount + fruitAmountFromTransaction);
        }
    }
}
