package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.operations.SupplyOperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private Transaction testTransaction;
    private OperationHandler supplyOperationTest;

    @Before
    public void setUp() {
        testTransaction = new Transaction("b", new Fruit("kiwi"), 10);
        supplyOperationTest = new SupplyOperationHandler();
    }

    @Test
    public void supplyTestQuantity_ok() {
        Fruit kiwi = new Fruit("kiwi");
        Storage.storage.put(kiwi, 5);
        supplyOperationTest.apply(testTransaction);
        Integer exepted = 15;
        assertEquals(exepted, Storage.storage.get(kiwi));
    }

    @Test (expected = NullPointerException.class)
    public void suppplyGetEmptyValue_notOk() {
        supplyOperationTest.apply(null);
    }

    @After
    public void clearStorage() {
        Storage.storage.clear();
    }
}
