package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit apple;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
        apple = new Fruit("apple");
    }

    @Test
    public void apply_ok() {
        operationHandler.apply(new Transaction("b", apple, 14));
        Integer actual = Storage.storage.get(apple);
        Integer expected = 14;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
