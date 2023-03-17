package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.BalanceHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final int ZERO_VALUE = 0;
    private static final int VALUE_LESS_THAN_ZERO = -1;
    private static final int VALUE_MORE_THAN_ZERO = 55;
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction fruitTransactionNull;
    private static BalanceHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction();
        balanceHandler = new BalanceHandler();
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_valueLessThanZero_notOk() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_LESS_THAN_ZERO);
        balanceHandler.initializeOperation(fruitTransaction);
    }

    @Test
    public void initializeOperation_valueIsZero_ok() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(ZERO_VALUE);
        balanceHandler.initializeOperation(fruitTransaction);
    }

    @Test
    public void initializeOperation_valueMoreThanZero_ok() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        balanceHandler.initializeOperation(fruitTransaction);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_fruitIsNull_notOk() {
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        balanceHandler.initializeOperation(fruitTransaction);
    }

    @Test
    public void initializeOperation_correctData_ok() {
        Storage.storage.clear();
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        balanceHandler.initializeOperation(fruitTransaction);
        int expected = VALUE_MORE_THAN_ZERO;
        int actual = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_fruitTransactionIsNull_notOk() {
        fruitTransactionNull = null;
        balanceHandler.initializeOperation(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
