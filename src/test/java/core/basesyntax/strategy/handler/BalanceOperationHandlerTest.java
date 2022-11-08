package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceOperationHandler(new StorageDaoImpl());
    }

    @Test
    public void execute_validDataAndEmptyStorage_ok() {
        Fruit banana = new Fruit("banana");
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, banana, 100);
        balanceHandler.execute(transaction);
        int expected = 100;
        int actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validDataAndNotEmptyStorage_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 20);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, banana, 100);
        balanceHandler.execute(transaction);
        int expected = 100;
        int actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void execute_null_notOk() {
        balanceHandler.execute(null);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
