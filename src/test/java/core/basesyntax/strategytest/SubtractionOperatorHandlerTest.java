package core.basesyntax.strategytest;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.SubtractionOperatorHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractionOperatorHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new SubtractionOperatorHandler();
    }

    @Before
    public void tearUp() {
        Storage.storage.put(new Fruit("peach"), 100);
        Storage.storage.put(new Fruit("apple"), 149);
        Storage.storage.put(new Fruit("banana"), 9);
        Storage.storage.put(new Fruit("tangerine"), 33);
        Storage.storage.put(new Fruit("pear"), 0);
        Storage.storage.put(new Fruit("grape"), 345);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_onExistData_Ok() {
        Transaction transaction = new Transaction(Operation.PURCHASE, "apple", 87);
        int expected = 62;
        handler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("apple"));
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s", expected, actual),
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_onNonExistData_NotOk() {
        Transaction transaction = new Transaction(Operation.PURCHASE, "blueberry", 85);
        handler.apply(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeResult_NotOk() {
        Transaction transaction = new Transaction(Operation.PURCHASE, "apple", 200);
        handler.apply(transaction);
    }
}
