package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final OperationHandler operationHandler = new BalanceOperationHandler();
    private static final Map<Fruit, Integer> storage = Storage.getAll();
    private static final Fruit DEFAULT_FRUIT = new Fruit("apple");

    @Test
    public void applyBalanceOperationHandler_appleIs25_isValid() {
        storage.put(DEFAULT_FRUIT, 20);
        operationHandler.apply(new Transaction("b", DEFAULT_FRUIT, 5));
        Integer expected = 25;
        Integer actual = storage.get(DEFAULT_FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void applyBalanceOperationHandler_appleIs10_isValid() {
        operationHandler.apply(new Transaction("b", DEFAULT_FRUIT, 10));
        Integer expected = 10;
        Integer actual = storage.get(DEFAULT_FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
