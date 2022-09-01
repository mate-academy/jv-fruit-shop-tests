package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balance;

    @BeforeClass
    public static void setUp() {
        balance = new BalanceOperationHandler();
    }

    @Test
    public void putFruits_ok() {
        Fruit banana = new Fruit("banana");
        balance.apply(new Transaction("b", banana, 10));
        assertEquals("Expected another value 10",
                Integer.valueOf(10),
                Storage.storage.get(banana));
    }

    @AfterClass
    public static void clean() {
        Storage.storage.clear();
    }
}
