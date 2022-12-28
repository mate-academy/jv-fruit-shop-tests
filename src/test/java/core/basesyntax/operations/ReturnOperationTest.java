package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static final String APPLE_NAME = "apple";
    private static final String ORANGE_NAME = "orange";
    private static final String NULL_NAME = null;
    private static final int BALANCE_AMOUNT = 10;
    private static final int RETURN_AMOUNT = 22;
    private static final int NEGATIVE_AMOUNT = -2;
    private static final int ZERO_AMOUNT = 0;
    private static ReturnOperation returnOperation;

    @BeforeClass
    public static void beforeClass() {
        returnOperation = new ReturnOperation();
    }

    @Before
    public void setUp() throws Exception {
        fruitStorage.put(APPLE_NAME, BALANCE_AMOUNT);
    }

    @Test
    public void returnApples_Ok() {
        returnOperation.action(APPLE_NAME, RETURN_AMOUNT);
        int expected = 32;
        int actual = fruitStorage.get(APPLE_NAME);
        assertEquals(expected, actual);
    }

    @Test(expected = OperationException.class)
    public void returnWithNullFruitName_NotOk() {
        returnOperation.action(NULL_NAME, RETURN_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void returnNegativeAmount_NotOk() {
        returnOperation.action(APPLE_NAME, NEGATIVE_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void returnZeroAmount_NotOk() {
        returnOperation.action(APPLE_NAME, ZERO_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void returnUnknownFruit_NotOk() {
        returnOperation.action(ORANGE_NAME, RETURN_AMOUNT);
    }

    @After
    public void tearDown() throws Exception {
        fruitStorage.clear();
    }
}
