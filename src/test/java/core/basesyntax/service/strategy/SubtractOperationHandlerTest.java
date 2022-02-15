package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.DailyActivity;
import core.basesyntax.model.Fruit;
import org.junit.Test;

public class SubtractOperationHandlerTest {
    private static final OperationHandler subtractOperationHandler =
            new SubtractOperationHandler();
    private static final DailyActivity DAILY_ACTIVITY_LINE_BALANCE =
            new DailyActivity("b", "banana", 50);
    private static final DailyActivity DAILY_ACTIVITY_LINE_PURCHASE =
            new DailyActivity("s", "banana", 10);
    private static final Fruit BANANA = new Fruit("banana");

    @Test
    public void operation_Ok() {
        Storage.storage.put(BANANA, DAILY_ACTIVITY_LINE_BALANCE.getAmount());
        subtractOperationHandler.operation(DAILY_ACTIVITY_LINE_PURCHASE);
        int expected = 40;
        int actual = Storage.storage.get(BANANA);
        assertEquals(expected, actual);
    }
}
