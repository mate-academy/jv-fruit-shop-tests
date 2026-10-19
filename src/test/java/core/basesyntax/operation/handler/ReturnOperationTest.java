package core.basesyntax.operation.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationTest {
    private static final String FRUIT = "banana";
    private ReturnOperation returnOperation;

    @Before
    public void setUp() {
        Storage.storage.clear();
        Storage.storage.put(FRUIT, 10);
        returnOperation = new ReturnOperation();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void handle_return_shouldAddQuantity() {
        returnOperation.handle(FRUIT, 15);

        int expected = 25;
        int actual = Storage.storage.get(FRUIT);
        assertEquals(expected, actual);
    }
}
