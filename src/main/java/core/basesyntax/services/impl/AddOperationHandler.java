package core.basesyntax.services.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.models.TransactionDto;
import core.basesyntax.services.OperationHandler;

public class AddOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public AddOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void apply(TransactionDto transactionDto) {
        String fruitName = transactionDto.getFruitName();
        int quantityToAdd = transactionDto.getQuantity();
        if (storageDao.contains(fruitName)) {
            storageDao.update(fruitName, quantityToAdd);
        } else {
            storageDao.add(fruitName, quantityToAdd);
        }
    }
}
