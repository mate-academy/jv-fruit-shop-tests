package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String DEFAULT_OPERATION = "b";
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final int DEFAULT_QUANTITY = 100;
    private static final String INCORRECT_OPERATION = "d";
    private static final int INCORRECT_QUANTITY = -1;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeAll() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT_NAME, DEFAULT_QUANTITY);
    }

    @Test
    public void getOperation_default_ok() {
        assertEquals(fruitTransaction.getOperation().getCode(), DEFAULT_OPERATION);
    }

    @Test
    public void getFruitName_default_ok() {
        assertEquals(fruitTransaction.getFruit(), DEFAULT_FRUIT_NAME);
    }

    @Test
    public void getQuantity_default_ok() {
        assertEquals(fruitTransaction.getQuantity(), DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void constructorFilledWithNull_notOk() {
        new FruitTransaction(null, null, null);
    }

    @Test(expected = RuntimeException.class)
    public void notRegisteredConstructorArgument_notOk() {
        new FruitTransaction(INCORRECT_OPERATION, DEFAULT_FRUIT_NAME, DEFAULT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void negativeQuantityArgument_notOk() {
        new FruitTransaction(DEFAULT_OPERATION, DEFAULT_FRUIT_NAME, INCORRECT_QUANTITY);
    }
}
