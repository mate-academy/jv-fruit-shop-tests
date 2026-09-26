package core.basesyntax.handlers;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.fruittransaction.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    private final FruitsDao dao;

    public PurchaseOperation(FruitsDao dao) {
        this.dao = dao;
    }

    @Override
    public void updateData(FruitTransaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction can't be null");
        }

        Integer currentFruitQuantity = dao.getFruitFromStorage(transaction.fruitName());

        if (currentFruitQuantity == null) {
            throw new IllegalArgumentException("Can't find the fruit " + transaction.fruitName());
        }

        int newQuantity = currentFruitQuantity - transaction.quantity();

        if (newQuantity < 0) {
            throw new IllegalArgumentException("New value of "
                    + transaction.fruitName()
                    + " can't be less than 0");
        }

        dao.addFruitToStorage(transaction.fruitName(), newQuantity);
    }
}
