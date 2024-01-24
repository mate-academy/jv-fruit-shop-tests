package core.basesyntax.operations;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    private static final Integer MIN_QUANTITY_FOR_OPERATION = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public Integer getHandler(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("Fruit transaction is null "
                    + fruitTransaction);
        }

        if (fruitTransaction.getQuantity() < MIN_QUANTITY_FOR_OPERATION) {
            throw new RuntimeException("Fruit quantity can't be negative "
                    + fruitTransaction.getQuantity());
        }

        int newQuantity;
        if (storageDao.getValue(fruitTransaction.getFruit())
                < fruitTransaction.getQuantity()) {
            throw new RuntimeException("Balance of "
                    + fruitTransaction.getFruit()
                    + " is less then "
                    + fruitTransaction.getQuantity());
        }

        newQuantity = storageDao.getValue(fruitTransaction.getFruit())
                - fruitTransaction.getQuantity();

        storageDao.add(new FruitTransaction(fruitTransaction.getOperation(),
                fruitTransaction.getFruit(),
                newQuantity));

        return newQuantity;
    }
}
