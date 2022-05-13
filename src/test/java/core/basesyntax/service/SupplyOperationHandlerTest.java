package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.StorageDao;
import core.basesyntax.storage.StorageDaoImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handleSupplyOperation_Ok() {
        FruitStorage.fruits.put(new Fruit("orange"), 42);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                new Fruit("orange"),
                60);
        operationHandler = new SupplyOperationHandler(storageDao);
        operationHandler.handle(transaction);
        int expected = 102;
        int actual = FruitStorage.fruits.get(new Fruit("orange"));
        assertEquals(expected, actual);
    }
}
