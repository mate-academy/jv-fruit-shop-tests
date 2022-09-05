package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.operations.BalanceOperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private Transaction testTransaction;
    private OperationHandler balanceOperationTest;
    private Fruit fruit;

    @Before
    public void setUp() {
        testTransaction = new Transaction("b", new Fruit("apple"), 10);
        balanceOperationTest = new BalanceOperationHandler();
    }

    @Test
    public void balanceTestQuantity_ok() {
        fruit = new Fruit("apple");
        balanceOperationTest.apply(testTransaction);
        Integer expected = 10;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test (expected = NullPointerException.class)
    public void balanceGetEmptyValue_notOk() {
        balanceOperationTest.apply(null);
    }

    @Test
    public void noSuchOperationTest_ok() {
        fruit = new Fruit("papaia");
        testTransaction = new Transaction("o", fruit, 200);
        balanceOperationTest.apply(testTransaction);
        assertEquals(1, Storage.storage.size());
    }

    @After
    public void clearStorage() {
        Storage.storage.clear();
    }
}
