package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperation;

    @BeforeClass
    public static void setUp() {
        returnOperation = new ReturnOperationHandler();
    }

    @After
    public void clean() {
        Storage.storage.clear();
    }

    @Test
    public void returnFruits_ok() {
        Fruit banana = new Fruit("banana");
        returnOperation.apply(new Transaction("b", banana, 10));
        assertEquals("Expected another value 10",
                Integer.valueOf(10),
                Storage.storage.get(banana));
    }

    @Test (expected = NullPointerException.class)
    public void returnNullTransaction_notOk() {
        returnOperation.apply(null);
    }

    @Test (expected = NullPointerException.class)
    public void supplyNullAmount_notOk() {
        returnOperation.apply(new Transaction("b", new Fruit("banana"), null));
    }
}
