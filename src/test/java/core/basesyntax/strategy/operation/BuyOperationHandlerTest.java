package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuyOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int QUANTITY = 20;
    private static FruitTransaction transaction;
    private static BuyOperationHandler buyOperation;

    @BeforeClass
    public static void beforeAll() {
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, FRUIT, QUANTITY);
        buyOperation = new BuyOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void purchaseOperation_handle_Ok() {
        Storage.fruits.put("banana", 40);
        buyOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        int expected = 20;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_notEnoughInStorage_NotOk() {
        Storage.fruits.put(FRUIT, 10);
        buyOperation.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_notFoundInStorage_NotOk() {
        buyOperation.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_withNullFruit_NotOk() {
        buyOperation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                null,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_withEmptyFruit_NotOk() {
        buyOperation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                EMPTY,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_withNullTransaction_NotOk() {
        buyOperation.handle(new FruitTransaction(null,
                FRUIT,
                QUANTITY));
    }
}
