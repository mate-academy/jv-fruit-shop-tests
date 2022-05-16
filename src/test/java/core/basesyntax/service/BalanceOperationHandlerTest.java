package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.StorageDao;
import core.basesyntax.storage.StorageDaoImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandler(storageDao);
    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handleBalanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                new Fruit("banana"),
                200);
        operationHandler.handle(transaction);
        int expected = 200;
        int actual = FruitStorage.fruits.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }
}
