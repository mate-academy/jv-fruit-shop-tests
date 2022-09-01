package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperation;

    @BeforeClass
    public static void setUp() {
        returnOperation = new BalanceOperationHandler();
    }

    @Test
    public void returnFruits_ok() {
        Fruit banana = new Fruit("banana");
        returnOperation.apply(new Transaction("b", banana, 10));
        assertEquals("Expected another value 10",
                Integer.valueOf(10),
                Storage.storage.get(banana));
    }

    @AfterClass
    public static void clean() {
        Storage.storage.clear();
    }
}
