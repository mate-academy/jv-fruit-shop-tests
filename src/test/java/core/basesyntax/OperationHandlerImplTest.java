package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.hadler.OperationHandler;
import core.basesyntax.hadler.impl.BalanceOperationHandler;
import core.basesyntax.hadler.impl.DecreaseOperationHandler;
import core.basesyntax.hadler.impl.IncreaseOperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerImplTest {
    private OperationHandler balanceHandler;
    private OperationHandler increaseHandler;
    private OperationHandler decreaseHandler;
    private StorageDao storageDao;

    @Before
    public void setup() {
        storageDao = new StorageDaoImpl();
        balanceHandler = new BalanceOperationHandler(storageDao);
        increaseHandler = new IncreaseOperationHandler(storageDao);
        decreaseHandler = new DecreaseOperationHandler(storageDao);
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }

    @Test
    public void handle_withBalanceOperationHandler_ok() {
        for (int i = 0; i < 10; i++) {
            balanceHandler.handle(new FruitTransaction(BALANCE, "Fruit " + i, i));
        }
        assertTrue(Storage.storage.size() == 10);
        for (int i = 0; i < 10; i++) {
            assertEquals(Integer.valueOf(i), Storage.storage.get("Fruit " + i));
        }
    }

    @Test
    public void handle_withDecreaseOperationHandler_ok() {
        Storage.storage.put("apple", 10);
        Storage.storage.put("banana", 20);
        Storage.storage.put("mango", 30);
        for (int i = 0; i < 10; i++) {
            decreaseHandler.handle(new FruitTransaction(PURCHASE, "apple", 1));
            decreaseHandler.handle(new FruitTransaction(PURCHASE, "banana", 1));
            decreaseHandler.handle(new FruitTransaction(PURCHASE, "mango", 1));
        }
        assertEquals(Integer.valueOf(0), Storage.storage.get("apple"));
        assertEquals(Integer.valueOf(10), Storage.storage.get("banana"));
        assertEquals(Integer.valueOf(20), Storage.storage.get("mango"));
    }

    @Test
    public void handle_withIncreaseOperationHandler_ok() {
        Storage.storage.put("apple", 10);
        Storage.storage.put("banana", 20);
        Storage.storage.put("mango", 30);
        for (int i = 0; i < 10; i++) {
            increaseHandler.handle(new FruitTransaction(SUPPLY, "apple", 1));
            increaseHandler.handle(new FruitTransaction(RETURN, "banana", 1));
            increaseHandler.handle(new FruitTransaction(SUPPLY, "mango", 1));
        }
        assertEquals(Integer.valueOf(20), Storage.storage.get("apple"));
        assertEquals(Integer.valueOf(30), Storage.storage.get("banana"));
        assertEquals(Integer.valueOf(40), Storage.storage.get("mango"));
    }

    @Test
    public void handle_withAllHandlers_ok() {
        for (int i = 0; i < 10; i++) {
            balanceHandler.handle(new FruitTransaction(BALANCE, "Fruit " + i, 10));
        }
        assertTrue(Storage.storage.size() == 10);
        for (int i = 0; i < 10; i++) {
            increaseHandler.handle(new FruitTransaction(SUPPLY, "Fruit " + i, 1));
            increaseHandler.handle(new FruitTransaction(RETURN, "Fruit " + i, 1));
        }
        for (int i = 0; i < 10; i++) {
            decreaseHandler.handle(new FruitTransaction(PURCHASE, "Fruit " + i, 2));
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(Integer.valueOf(10), storageDao.getQuantity("Fruit " + i));
        }
    }
}
