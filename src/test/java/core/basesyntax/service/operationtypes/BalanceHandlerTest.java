package core.basesyntax.service.operationtypes;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private static int expectedAmount = 100;
    private static final String fruitName = "apple";
    private static final int amount = 100;
    private static OperationTypeHandler operationTypeHandler;

    @Before
    public void setUp() throws Exception {
        Storage.fruits.clear();
        operationTypeHandler = new BalanceHandler();
    }

    @Test
    public void apply_correctData_Ok() {
        operationTypeHandler.apply(fruitName, amount);
        int expected = expectedAmount;
        int actual = Storage.fruits.get(fruitName);
        assertEquals(expected, actual);
    }
}
