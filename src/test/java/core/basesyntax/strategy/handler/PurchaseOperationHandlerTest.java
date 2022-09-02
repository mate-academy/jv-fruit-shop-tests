package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new PurchaseOperationHandler();
    }

    @After
    public void tearDown() throws Exception {
        Storage.getStorage().clear();
    }

    @Test
    public void apply_PurchaseFruit_ok() {
        Fruit banana = new Fruit("banana");
        Storage.getStorage().put(banana, 10);
        operationHandler.apply(new Transaction("p", banana, 5));
        assertEquals("Expected another value",
                Integer.valueOf(5), Storage.getStorage().get(banana));
    }

    @Test(expected = RuntimeException.class)
    public void apply_PurchaseMoreThanBalance_ThrowException_ok() {
        Fruit banana = new Fruit("banana");
        Storage.getStorage().put(banana, 10);
        operationHandler.apply(new Transaction("p", banana, 15));
    }
}
