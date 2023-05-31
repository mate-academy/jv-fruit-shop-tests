package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        supplyOperationHandler = new SupplyOperationHandler();
        fruit = new Fruit("banana");
    }

    @Test
    public void supplyOperationHandlerIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation.SUPPLY,
                fruit, 8);
        Storage.getFruitsMap().put(fruit, 8);
        Integer expected = 16;
        supplyOperationHandler.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperationHandlerIsInvalid_NotOk() {
        supplyOperationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getFruitsMap().clear();
    }
}
