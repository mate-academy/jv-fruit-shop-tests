package core.basesyntax.operation.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private static final String FRUIT = "orange";
    private SupplyOperation supplyOperation;

    @Before
    public void setUp() {
        Storage.storage.clear();
        Storage.storage.put(FRUIT, 5);
        supplyOperation = new SupplyOperation();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void handle_supply_shouldAddQuantity() {
        supplyOperation.handle(FRUIT, 10);

        int expected = 15;
        int actual = Storage.storage.get(FRUIT);
        assertEquals(expected, actual);
    }
}
