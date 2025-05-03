package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyHandler = new SupplyOperationHandler();
    }

    @Test
    public void supplierIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation
                .SUPPLY, new Fruit("apple"), 33);
        Storage.getStorage().put(new Fruit("apple"), 22);
        supplyHandler.apply(transaction);
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        Integer expected = 55;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void supplierIsValid_NotOk() {
        supplyHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorage().clear();
    }
}
