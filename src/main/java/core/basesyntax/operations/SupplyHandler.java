package core.basesyntax.operations;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    private static final int MIN_OPERATION_QUANTITY = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public Integer getHandler(FruitTransaction fruitTransaction) {
        int newQuantity;
        if (fruitTransaction == null) {
            throw new RuntimeException("Fruit transaction is null "
                    + fruitTransaction);
        }

        if (fruitTransaction.getQuantity() < MIN_OPERATION_QUANTITY) {
            throw new RuntimeException("Fruit quantity can't be negative "
                    + fruitTransaction.getQuantity());
        }

        newQuantity = storageDao.getValue(fruitTransaction.getFruit())
                + fruitTransaction.getQuantity();

        storageDao.add(new FruitTransaction(fruitTransaction.getOperation(),
                fruitTransaction.getFruit(),
                newQuantity));

        return newQuantity;
    }
}
