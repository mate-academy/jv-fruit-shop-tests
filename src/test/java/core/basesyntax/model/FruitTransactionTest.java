package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {

    private static final String DEFAULT_OPERATION = "b";
    private static final String DEFAULT_FRUIT = "banana";
    private static final int DEFAULT_QUANTITY = 100;
    private static FruitTransaction defaultFruitTransaction;

    @BeforeClass
    public static void setUp() {
        defaultFruitTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_FRUIT, DEFAULT_QUANTITY
        );
    }

    @Test
    public void getOperation_defaultCase_ok() {
        assertEquals(defaultFruitTransaction.getOperation(),
                FruitTransaction.Operation.BALANCE);
    }

    @Test
    public void getFruit_defaultCase_ok() {
        assertEquals(defaultFruitTransaction.getFruit(), DEFAULT_FRUIT);
    }

    @Test
    public void getQuantity_defaultCase_ok() {
        assertEquals(defaultFruitTransaction.getQuantity(), DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_unsupportedOperation_notOk() {
        new FruitTransaction("k", DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_nullArguments_notOk() {
        new FruitTransaction(null, null, null);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_firstArgumentNull_notOk() {
        new FruitTransaction(null, DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_secondArgumentNull_notOk() {
        new FruitTransaction(DEFAULT_OPERATION, null, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_thirdArgumentNull_notOk() {
        new FruitTransaction(DEFAULT_OPERATION, DEFAULT_FRUIT, null);
    }
}
