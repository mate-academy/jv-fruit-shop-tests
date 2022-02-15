package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.DailyActivity;
import core.basesyntax.model.Fruit;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final OperationHandler balanceOperationHandler = new BalanceOperationHandler();
    private static final DailyActivity DAILY_ACTIVITY_LINE_BALANCE =
            new DailyActivity("b", "banana", 50);
    private static final Fruit fruit = new Fruit("banana");

    @Test
    public void operation_Ok() {
        balanceOperationHandler.operation(DAILY_ACTIVITY_LINE_BALANCE);
        int expected = 50;
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }
}
