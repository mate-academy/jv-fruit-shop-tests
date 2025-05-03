package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.handler.OperationTypeHandler;
import core.basesyntax.service.operation.handler.ReturnHandler;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private static int expectedAmount = 120;
    private static final String fruitName = "apple";
    private static final int amount = 100;
    private static final int returnAmount = 20;
    private static OperationTypeHandler operationTypeHandler;

    @Before
    public void setUp() throws Exception {
        Storage.fruits.clear();
        Storage.fruits.put(fruitName, amount);
        operationTypeHandler = new ReturnHandler();
    }

    @Test
    public void apply_returnHandler_Ok() {
        operationTypeHandler.apply(fruitName, returnAmount);
        int expected = expectedAmount;
        int actual = Storage.fruits.get(fruitName);
        assertEquals(expected, actual);
    }
}
