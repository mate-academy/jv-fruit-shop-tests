package core.basesyntax.transactionsservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpL;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.Test;

class OperationBalanceTest {
    private Transaction transaction;
    private FruitStorageDao fruitStorageDao = new FruitStorageDaoImpL();
    private OperationHandler operationHandler = new OperationBalance(fruitStorageDao);
    private Fruit fruit;

    @Test
    void validInputDataProceedOperationBalance() {
        fruit = new Fruit("apple");
        transaction = new Transaction(Transaction.TransactionType.BALANCE, fruit, 1000);
        int expectedValue = transaction.getAmount();
        operationHandler.proceed(transaction);
        assertEquals(expectedValue, FruitsStorage.storage.get(fruit));
    }
}
