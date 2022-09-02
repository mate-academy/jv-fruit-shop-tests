package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationImplTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void setUp() throws Exception {
        operationHandler = new ReturnOperationImpl();
        apple = new Fruit("apple");
    }

    @Test
    public void applyReturn_OK() {
        Storage.storage.put(apple, 10);
        operationHandler.apply(new Transaction("p", apple, 10));
        Integer expected = 20;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }
}
