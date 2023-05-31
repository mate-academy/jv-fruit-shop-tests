package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static ReturnOperation returnOperation;

    @BeforeClass
    public static void beforeClass() {
        returnOperation = new ReturnOperation();
    }

    @Before
    public void setUp() throws Exception {
        fruitStorage.put("apple", 10);
    }

    @Test
    public void returnApples_Ok() {
        returnOperation.action("apple", 22);
        int expected = 32;
        int actual = fruitStorage.get("apple");
        assertEquals(expected, actual);
    }

    @Test(expected = OperationException.class)
    public void returnWithNullFruitName_NotOk() {
        returnOperation.action(null, 22);
    }

    @Test(expected = OperationException.class)
    public void returnNegativeAmount_NotOk() {
        returnOperation.action("apple", -2);
    }

    @Test(expected = OperationException.class)
    public void returnZeroAmount_NotOk() {
        returnOperation.action("apple", 0);
    }

    @Test(expected = OperationException.class)
    public void returnUnknownFruit_NotOk() {
        returnOperation.action("orange", 22);
    }

    @After
    public void tearDown() throws Exception {
        fruitStorage.clear();
    }
}
