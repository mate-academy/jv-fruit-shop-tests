package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractionOperationHandlerTest {
    private static SubtractionOperationHandler subtractionOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        subtractionOperationHandler = new SubtractionOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
        Storage.storage.put(new Fruit("banana"), 40);
    }

    @Test
    public void subtractionOperation_Ok() {
        subtractionOperationHandler.apply(new FruitTransaction("b","banana",20));
        Integer actual = Storage.storage.get(new Fruit("banana"));
        Integer expected = 20;
        assertEquals(expected, actual);
    }
}

