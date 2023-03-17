package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Utils;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuyOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int EXPECTED = 20;
    private static FruitTransaction transaction;
    private static BuyOperationHandler buyOperation;

    @BeforeClass
    public static void beforeAll() {
        Storage.fruits.clear();
        transaction = Utils.createTransaction(FruitTransaction.Operation.PURCHASE, FRUIT, EXPECTED);
        buyOperation = new BuyOperationHandler();
    }

    @Test
    public void purchaseOperation_handle_Ok() {
        Storage.fruits.put("banana", 40);
        buyOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_notEnoughInStorage_NotOk() {
        buyOperation.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_notFoundInStorage_NotOk() {
        buyOperation.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_withNullFruit_NotOk() {
        buyOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                null,
                EXPECTED));
        int expected = 20;
        int actual = Storage.fruits.get(null);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_withEmptyFruit_NotOk() {
        buyOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                EMPTY,
                EXPECTED));
        int expected = 20;
        int actual = Storage.fruits.get(EMPTY);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_withNullTransaction_NotOk() {
        buyOperation.handle(Utils.createTransaction(null,
                FRUIT,
                EXPECTED));
        int expected = 20;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}
