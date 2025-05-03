package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balance_validData_Ok() {
        Storage.storage.put(new Fruit("mango"), 40);
        balanceOperationHandler.process(new Fruit("mango"), 30);
        int expected = 30;
        int actual = Storage.storage.get(new Fruit("mango"));
        assertEquals(expected, actual);
    }

    @Test
    public void balance_newFruit_Ok() {
        balanceOperationHandler.process(new Fruit("mango"), 1);
        int expected = 1;
        int actual = Storage.storage.get(new Fruit("mango"));
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}

