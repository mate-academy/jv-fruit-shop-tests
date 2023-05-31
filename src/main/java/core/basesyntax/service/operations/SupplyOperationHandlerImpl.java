package core.basesyntax.service.operations;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.template.FruitTransaction;

public class SupplyOperationHandlerImpl implements OperationHandler {
    private StorageDao storageDao;

    public SupplyOperationHandlerImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("Can't handle null transaction "
                    + fruitTransaction);
        }

        Integer oldQuantity;
        Integer newQuantity;

        if (storageDao.get(fruitTransaction.getFruit()) == null) {
            newQuantity = fruitTransaction.getQuantity();
        } else {
            oldQuantity = storageDao.get(fruitTransaction.getFruit());
            newQuantity = oldQuantity + fruitTransaction.getQuantity();
        }

        storageDao.put(fruitTransaction.getFruit(), newQuantity);
    }
}
