package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void init() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void validSupplyTransaction_Ok() {
        Storage.getStorage().put(new Fruit("banana"), 100);
        Transaction transaction = new Transaction("s", new Fruit("banana"), 50);
        supplyOperationHandler.apply(transaction);
        int expected = 150;
        int actual = Storage.getStorage().get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
