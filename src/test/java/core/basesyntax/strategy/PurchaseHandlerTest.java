package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.PurchaseHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final String NON_EXISTING_FRUIT = "watermelon";
    private static final int ZERO_VALUE = 0;
    private static final int VALUE_LESS_THAN_ZERO = -1;
    private static final int VALUE_MORE_THAN_ZERO = 55;
    private static final int VALUE_TO_REMOVE = 23;
    private static final int VALUE_AFTER_REMOVE_EXPECTED = 32;
    private static FruitTransaction fruitTransaction;
    private static PurchaseHandler purchaseHandler;
    private static int expected;

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction();
        purchaseHandler = new PurchaseHandler();
        Storage.storage.put(DEFAULT_FRUIT_NAME, VALUE_MORE_THAN_ZERO);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_valueLessThanZero_notOk() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_LESS_THAN_ZERO);
        purchaseHandler.initializeOperation(fruitTransaction);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_valueIsZero_notOk() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(ZERO_VALUE);
        purchaseHandler.initializeOperation(fruitTransaction);
    }

    @Test
    public void initializeOperation_valueMoreThanZero_ok() {
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        purchaseHandler.initializeOperation(fruitTransaction);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_fruitIsNull_notOk() {
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        purchaseHandler.initializeOperation(fruitTransaction);
    }

    @Test(expected = FruitShopException.class)
    public void initializeOperation_fruitIsNotInStorage_notOk() {
        fruitTransaction.setFruit(NON_EXISTING_FRUIT);
        fruitTransaction.setQuantity(VALUE_MORE_THAN_ZERO);
        purchaseHandler.initializeOperation(fruitTransaction);
    }

    @Test
    public void initializeOperation_correctData_ok() {
        expected = VALUE_AFTER_REMOVE_EXPECTED;
        fruitTransaction.setFruit(DEFAULT_FRUIT_NAME);
        fruitTransaction.setQuantity(VALUE_TO_REMOVE);
        purchaseHandler.initializeOperation(fruitTransaction);
        int actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }
}
