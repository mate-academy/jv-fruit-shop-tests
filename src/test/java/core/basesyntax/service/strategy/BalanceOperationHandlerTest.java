package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineInformation;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandlerBalance;

    @BeforeClass
    public static void setUp() {
        operationHandlerBalance = new BalanceOperationHandler();
    }

    @Test
    public void operateValidBalance_ok() {
        operationHandlerBalance.operate(new LineInformation(
                "b", new Fruit("banana"), 40));
        Integer expected = 40;
        assertEquals(expected, Storage.storage.get(new Fruit("banana")));
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
