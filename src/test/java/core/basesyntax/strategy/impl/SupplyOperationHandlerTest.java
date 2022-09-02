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
    public static void beforeClass() throws Exception {
        supplyHandler = new SupplyOperationHandler();
    }

    @Test
    public void supplierIs_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation
                .SUPPLY, new Fruit("apple"), 33);
        Storage.getStorage().put(new Fruit("apple"), 22);
        Integer expected = 55;
        supplyHandler.apply(transaction);
        assertEquals(expected, Storage.getStorage().get(new Fruit("apple")));
    }

    @Test(expected = RuntimeException.class)
    public void supplier_NotOk() {
        supplyHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getStorage().clear();
    }
}
