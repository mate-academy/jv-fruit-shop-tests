package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final String FRUIT = "orange";
    private OperationHandler purchaseHandler;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseOperationStrategy();
        Storage.fruits.put(FRUIT, 50);
    }

    @Test
    public void handle_purchaseHandle_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(20);
        purchaseHandler.handle(transaction);
        Integer expected = 30;
        Integer actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
