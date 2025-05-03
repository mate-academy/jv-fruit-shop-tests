package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationImplTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void setUp() {
        operationHandler = new SupplyOperationImpl();
        apple = new Fruit("apple");
    }

    @Test
    public void applySupply_OK() {
        Storage.storage.put(apple, 20);
        operationHandler.apply(new Transaction("s", apple, 40));
        Integer expected = 60;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }
}
