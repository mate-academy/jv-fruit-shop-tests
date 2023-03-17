package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.SupplyHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final int ZERO_VALUE = 0;
    private static final int VALUE_LESS_THAN_ZERO = -1;
    private static final int VALUE_MORE_THAN_ZERO = 55;
    private static final int VALUE_TO_ADD = 23;
    private static final int VALUE_AFTER_ADDING_EXPECTED = 78;
    private static FruitTransaction fruitTransaction;
    private static SupplyHandler supplyHandler;
    private static int expected;

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction();
        supplyHandler = new SupplyHandler();
        Storage.storage.put(DEFAULT_FRUIT_NAME, VALUE_MORE_THAN_ZERO);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_valueLessThanZero_notOk() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_LESS_THAN_ZERO);
        supplyHandler.initializeOperation(fruitTransaction);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_valueIsZero_notOk() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(ZERO_VALUE);
        supplyHandler.initializeOperation(fruitTransaction);
    }

    @Test
    public void initializeOperation_valueMoreThanZero_ok() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        supplyHandler.initializeOperation(fruitTransaction);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_fruitIsNull_notOk() {
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        supplyHandler.initializeOperation(fruitTransaction);
    }

    @Test
    public void initializeOperation_correctData_ok() {
        expected = VALUE_AFTER_ADDING_EXPECTED;
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_TO_ADD);
        supplyHandler.initializeOperation(fruitTransaction);
        int actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }
}
