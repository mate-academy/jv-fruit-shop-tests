package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.StorageDao;
import core.basesyntax.storage.StorageDaoImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
        operationHandler = new ReturnOperationHandler(storageDao);
    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handleReturnOperation_Ok() {
        FruitStorage.fruits.put(new Fruit("kiwi"), 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                new Fruit("kiwi"),
                10);
        operationHandler.handle(transaction);
        int expected = 30;
        int actual = FruitStorage.fruits.get(new Fruit("kiwi"));
        assertEquals(expected, actual);
    }
}
