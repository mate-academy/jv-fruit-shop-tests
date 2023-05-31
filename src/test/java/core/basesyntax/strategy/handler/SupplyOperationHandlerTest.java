package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyOperationHandler(new StorageDaoImpl());
    }

    @Test
    public void execute_validDataAndEmptyStorage_ok() {
        Fruit banana = new Fruit("banana");
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, banana, 100);
        supplyHandler.execute(transaction);
        int expected = 100;
        int actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validDataAndNotEmptyStorage_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 20);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, banana, 100);
        supplyHandler.execute(transaction);
        int expected = 120;
        int actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
