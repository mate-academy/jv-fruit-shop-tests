package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void setSupplyOperationHandlerIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("banana"), 8);
        Storage.getFruitsMap().put(new Fruit("banana"), 8);
        Integer expected = 16;
        supplyOperationHandler.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(new Fruit("banana")));
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperationHandlerIsInvalid_NotOk() {
        supplyOperationHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getFruitsMap().clear();
    }
}
