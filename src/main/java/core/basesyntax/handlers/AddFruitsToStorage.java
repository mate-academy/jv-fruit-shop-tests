package core.basesyntax.handlers;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.fruittransaction.FruitTransaction;

public class AddFruitsToStorage {
    static void add(FruitTransaction transaction, FruitsDao dao) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction can't be null");
        }

        Integer currentFruitQuantity = dao.getFruitFromStorage(transaction.fruitName());

        int newQuantity = currentFruitQuantity != null
                ? currentFruitQuantity + transaction.quantity()
                : transaction.quantity();

        dao.addFruitToStorage(transaction.fruitName(), newQuantity);
    }
}
