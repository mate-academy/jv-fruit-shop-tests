package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int QUANTITY = 20;
    private static FruitTransaction transaction;
    private static SupplyOperationHandler supplyOperation;

    @BeforeClass
    public static void beforeAll() {
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, FRUIT, QUANTITY);
        supplyOperation = new SupplyOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void supplyOperation_handlePut_Ok() {
        supplyOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        int expected = 20;
        assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_handleAdd_Ok() {
        Storage.fruits.put(FRUIT, QUANTITY);
        supplyOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        int expected = 40;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperation_notFoundInStorage_NotOk() {
        supplyOperation.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperation_withNullFruit_NotOk() {
        supplyOperation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                null,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperation_withEmptyFruit_NotOk() {
        supplyOperation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                EMPTY,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperation_withNullTransaction_NotOk() {
        supplyOperation.handle(new FruitTransaction(null, FRUIT, QUANTITY));
    }
}
