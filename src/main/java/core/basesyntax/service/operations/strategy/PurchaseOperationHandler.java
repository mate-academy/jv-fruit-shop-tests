package core.basesyntax.service.operations.strategy;

import core.basesyntax.dao.FruitTransactionDao;

public class PurchaseOperationHandler implements OperationHandler {

    private final FruitTransactionDao fruitTransactionDao;

    public PurchaseOperationHandler(FruitTransactionDao fruitTransactionDao) {
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public void performOperation(String name, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Can't set negative quantity");
        }

        int newQuantity = fruitTransactionDao.getByName(name) - quantity;

        if (newQuantity < 0) {
            throw new RuntimeException(
                    "There aren't enough fruit to perform operations");
        }
        fruitTransactionDao.update(name, newQuantity);
    }
}
