package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.DailyActivity;
import core.basesyntax.model.Fruit;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static final OperationHandler addOperationHandler = new AddOperationHandler();

    private static final DailyActivity DAILY_ACTIVITY_LINE_BALANCE =
            new DailyActivity("b", "banana", 50);
    private static final DailyActivity DAILY_ACTIVITY_LINE_SUPPLY =
            new DailyActivity("s", "banana", 100);
    private static final Fruit BANANA = new Fruit("banana");

    @Test
    public void operation_Ok() {
        Storage.storage.put(BANANA,DAILY_ACTIVITY_LINE_BALANCE.getAmount());
        addOperationHandler.operation(DAILY_ACTIVITY_LINE_SUPPLY);
        int expected = 150;
        int actual = Storage.storage.get(BANANA);
        assertEquals(expected, actual);
    }
}
