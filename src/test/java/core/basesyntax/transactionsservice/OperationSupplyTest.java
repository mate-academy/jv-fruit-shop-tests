package core.basesyntax.transactionsservice;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpL;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationSupplyTest {
    private Transaction transaction;
    private FruitStorageDao fruitStorageDao = new FruitStorageDaoImpL();
    private OperationHandler operationHandler = new OperationSupply(fruitStorageDao);
    private Fruit fruit;

    @Test
    void validInputDataProceedOperationBalance() {
        fruit = new Fruit("apple");
        transaction = new Transaction(Transaction.TransactionType.SUPPLY, fruit, 1000);
        int expectedValue = FruitsStorage.storage.getOrDefault(fruit, 0)
                + transaction.getAmount();
        operationHandler.proceed(transaction);
        assertEquals(expectedValue, FruitsStorage.storage.get(fruit));
    }
}
