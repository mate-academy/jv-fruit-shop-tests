package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdditionOperationHandlerTest {
    private static AdditionOperationHandler additionOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        additionOperationHandler = new AdditionOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void apply_Ok() {
        additionOperationHandler.apply(new FruitTransaction("b","banana",20));
        additionOperationHandler.apply(new FruitTransaction("b","banana",20));
        Integer actual = Storage.storage.get(new Fruit("banana"));
        Integer expected = 40;
        assertEquals(expected, actual);
    }
}
