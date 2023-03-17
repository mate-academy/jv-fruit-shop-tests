package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Utils;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int EXPECTED = 20;
    private static FruitTransaction transaction;
    private static SupplyOperationHandler supplyOperation;

    @BeforeClass
    public static void beforeAll() {
        Storage.fruits.clear();
        transaction = Utils.createTransaction(FruitTransaction.Operation.SUPPLY, FRUIT, EXPECTED);
        supplyOperation = new SupplyOperationHandler();
    }

    @Test
    public void supplyOperation_handle_Ok() {
        supplyOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperation_notFoundInStorage_NotOk() {
        supplyOperation.handle(null);
    }

    @Test
    public void supplyOperation_withNullFruit_Ok() {
        supplyOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                null,
                EXPECTED));
        int expected = 20;
        int actual = Storage.fruits.get(null);
        assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_withEmptyFruit_Ok() {
        supplyOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                EMPTY,
                EXPECTED));
        int expected = 20;
        int actual = Storage.fruits.get(EMPTY);
        assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_withNullTransaction_NotOk() {
        Storage.fruits.clear();
        supplyOperation.handle(Utils.createTransaction(null,
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
