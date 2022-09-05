package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.operations.ReturnOperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationTest {
    private OperationHandler returnOperationTest;
    private Transaction testTransaction;

    @Before
    public void setUp() {
        testTransaction = new Transaction("b", new Fruit("apple"), 10);
        returnOperationTest = new ReturnOperationHandler();
    }

    @Test
    public void returnTestQuantity_ok() {
        Fruit fruit = new Fruit("apple");
        Storage.storage.put(fruit, 0);
        returnOperationTest.apply(testTransaction);
        Integer expected = 10;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test (expected = NullPointerException.class)
    public void suppplyGetEmptyValue_notOk() {
        returnOperationTest.apply(null);
    }

    @After
    public void clearStorage() {
        Storage.storage.clear();
    }
}
