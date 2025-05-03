package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @After
    public void tearDown() throws Exception {
        Storage.getStorage().clear();
    }

    @Test
    public void apply_SupplyOperation_ok() {
        Fruit banana = new Fruit("banana");
        Storage.getStorage().put(banana, 0);
        supplyOperationHandler.apply(new Transaction("s", banana, 15));
        assertEquals("Expected another value",
                Integer.valueOf(15), Storage.getStorage().get(banana));
    }
}
