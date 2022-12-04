package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static String FRUIT = "apple";
    private OperationHandler purchaseHandler;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandler();
        Storage.fruits.put(FRUIT, 50);
    }

    @Test
    public void addPurchaseHandler_enoughFruits_ok() {
        FruitTransaction purchase = new FruitTransaction(Operation.getByCode("p"), FRUIT, 20);
        purchaseHandler.operate(purchase);
        int expected = 30;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }
    
    @Test
    public void addPurchaseHandler_negativeQty_ok() {
        FruitTransaction purchase = new FruitTransaction(Operation.getByCode("p"), FRUIT, -20);
        purchaseHandler.operate(purchase);
        int expected = 30;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }
    
    @Test(expected = RuntimeException.class)
    public void addPurchaseHandler_notEnoughFruits_notOk() {
        FruitTransaction purchase = new FruitTransaction(Operation.getByCode("p"), FRUIT, 51);
        purchaseHandler.operate(purchase);
    }
    
    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
