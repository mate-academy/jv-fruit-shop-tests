package core.basesyntax.transactionsservice;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpL;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationReturnTest {
    private Transaction transaction;
    private FruitStorageDao fruitStorageDao = new FruitStorageDaoImpL();
    private OperationHandler operationHandler = new OperationReturn(fruitStorageDao);
    private Fruit fruit;

    @Test
    void validInputDataProceedOperationBalance() {
        fruit = new Fruit("apple");
        transaction = new Transaction(Transaction.TransactionType.RETURN, fruit, 1000);
        int expectedValue = FruitsStorage.storage.getOrDefault(fruit,0)
                + transaction.getAmount();
        operationHandler.proceed(transaction);
        assertEquals(expectedValue, FruitsStorage.storage.get(fruit));
    }

//    @Test
//    void proceed() {
//    }
}