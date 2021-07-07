package core.basesyntax.strategytest;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.AdditionOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdditionOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new AdditionOperationHandler();
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
        Transaction transaction = new Transaction(Operation.SUPPLY, "banana", 100);
        int expected = 109;
        handler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s", expected, actual),
                expected, actual);
    }

    @Test
    public void apply_onNonExistData_Ok() {
        Transaction transaction = new Transaction(Operation.SUPPLY, "strawberry", 27);
        int expected = 27;
        handler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("strawberry"));
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s", expected, actual),
                expected, actual);
    }

    @Test
    public void apply_operationReturn_Ok() {
        Transaction transaction = new Transaction(Operation.RETURN, "melon", 36);
        int expected = 36;
        handler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("melon"));
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s", expected, actual),
                expected, actual);
    }
}

