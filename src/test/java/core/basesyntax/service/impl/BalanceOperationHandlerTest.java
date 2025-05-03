package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
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
        public void process_initialStorageIsEmpty_ok() {
        Integer expected = 8;
        balanceOperationHandler.process(new FruitTransaction("b", new Fruit("apple"), 8));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void process_initialStorageNotEmpty_ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        Integer expected = 0;
        balanceOperationHandler.process(new FruitTransaction("b", new Fruit("apple"), 0));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @After
         public void afterEach() {
        Storage.storage.clear();
    }
}
