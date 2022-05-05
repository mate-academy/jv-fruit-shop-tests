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
    public void balance_Ok() {
        balanceOperationHandler.process(new Fruit("mango"), 1);
        Integer actual = 1;
        assertEquals(actual, Storage.storage.get(new Fruit("mango")));
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}

