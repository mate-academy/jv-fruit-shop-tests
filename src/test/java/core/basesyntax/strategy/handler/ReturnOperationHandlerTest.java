package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new ReturnOperationHandler();
    }

    @After
    public void tearDown() throws Exception {
        Storage.getStorage().clear();
    }

    @Test
    public void apply_ReturnFruit_ok() {
        Fruit banana = new Fruit("banana");
        Storage.getStorage().put(banana, 10);
        operationHandler.apply(new Transaction("r", banana, 5));
        assertEquals("Expected another value",
                Integer.valueOf(15), Storage.getStorage().get(banana));
    }
}
