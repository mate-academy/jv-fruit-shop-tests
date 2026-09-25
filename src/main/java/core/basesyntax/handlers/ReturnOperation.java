package core.basesyntax.handlers;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.fruittransaction.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    private final FruitsDao dao;

    public ReturnOperation(FruitsDao dao) {
        this.dao = dao;
    }

    @Override
    public void updateData(FruitTransaction transaction) {
        Integer currentFruitQuantity = dao.getFruitFromStorage(transaction.fruitName());

        int newQuantity = currentFruitQuantity != null
                ? currentFruitQuantity + transaction.quantity()
                : transaction.quantity();

        dao.addFruitToStorage(transaction.fruitName(), newQuantity);
    }
}
