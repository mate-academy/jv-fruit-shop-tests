package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperation;

    @BeforeClass
    public static void beforeClass() {
        supplyOperation = new SupplyOperationHandler();
    }

    @Test
    public void supply_Ok() {
        Storage.storage.put(new Fruit("banana"), 10);
        supplyOperation.process(new Fruit("banana"), 20);
        Integer actual = 30;
        assertEquals(actual, Storage.storage.get(new Fruit("banana")));
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
