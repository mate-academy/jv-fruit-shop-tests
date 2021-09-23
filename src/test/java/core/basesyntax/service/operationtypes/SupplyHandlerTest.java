package core.basesyntax.service.operationtypes;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private static int expectedAmount = 110;
    private static final String fruitName = "apple";
    private static final int amount = 10;
    private static final int supplyAmount = 100;
    private static OperationTypeHandler operationTypeHandler;

    @Before
    public void setUp() throws Exception {
        Storage.fruits.clear();
        Storage.fruits.put(fruitName, amount);
        operationTypeHandler = new SupplyHandler();
    }

    @Test
    public void apply_supplyHandler_Ok() {
        operationTypeHandler.apply(fruitName, supplyAmount);
        int expected = expectedAmount;
        int actual = Storage.fruits.get(fruitName);
        assertEquals(expected, actual);
    }
}
