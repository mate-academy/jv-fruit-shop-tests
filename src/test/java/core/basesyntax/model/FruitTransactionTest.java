package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION = BALANCE;
    private static final int DEFAULT_QUANTITY = 50;
    private static final int DEFAULT_QUANTITY_NEGATIVE_VALUE = -10;
    private static FruitTransaction defaultTransaction;
    private static final String DEFAULT_FRUIT_APPLE = "apple";

    @BeforeClass
    public static void setUp() {
        defaultTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY
        );
    }

    @Test(expected = RuntimeException.class)
    public void calculateAndStore_nullOperation_notOk() {
        new FruitTransaction(null, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY);
        fail("Test failed! Operation cannot be null!");
    }

    @Test(expected = RuntimeException.class)
    public void calculateAndStore_nullFruit_notOk() {
        new FruitTransaction(BALANCE, null, DEFAULT_QUANTITY);
        fail("Test failed! Fruit cannot be null!");
    }

    @Test(expected = RuntimeException.class)
    public void calculateAndStore_negativeQuantity_notOk() {
        new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_NEGATIVE_VALUE);
        fail("Test failed! Quantity cannot have negative value!");
    }

    @Test
    public void getOperation_ok() {
        assertEquals("Test failed, the method must return "
                        + BALANCE + ", but was " + defaultTransaction.getOperation(),
                BALANCE, defaultTransaction.getOperation());
    }

    @Test
    public void getFruit_ok() {
        assertEquals("Test failed, the method must return "
                        + DEFAULT_FRUIT_APPLE + ", but was " + defaultTransaction.getFruit(),
                DEFAULT_FRUIT_APPLE, defaultTransaction.getFruit());
    }

    @Test
    public void getQuantity_ok() {
        assertEquals("Test failed, the method must return "
                        + DEFAULT_QUANTITY + ", but was " + defaultTransaction.getQuantity(),
                DEFAULT_QUANTITY, defaultTransaction.getQuantity());
    }
}
