package core.basesyntax.transactionsservice;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpL;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationPurchaseTest {
    private Transaction transaction;
    private FruitStorageDao fruitStorageDao = new FruitStorageDaoImpL();
    private OperationHandler operationHandler = new OperationPurchase(fruitStorageDao);
    private Fruit fruit;

    @Test
    void invalidInputDataProceedOperationPurchase() {
        fruit = new Fruit("apple");
        FruitsStorage.storage.put(fruit, 800);
        transaction = new Transaction(Transaction.TransactionType.PURCHASE, fruit, 1000);
        assertThrows(RuntimeException.class, () -> operationHandler.proceed(transaction));
    }

    @Test
    void validInputDataProceedOperationPurchase() {
        fruit = new Fruit("apple");
        FruitsStorage.storage.put(fruit, 1000);
        transaction = new Transaction(Transaction.TransactionType.PURCHASE, fruit, 800);
        int expectedvalue = FruitsStorage.storage.getOrDefault(fruit, 0)
                - transaction.getAmount();
        operationHandler.proceed(transaction);
        assertEquals(expectedvalue, FruitsStorage.storage.get(fruit));
    }
}
