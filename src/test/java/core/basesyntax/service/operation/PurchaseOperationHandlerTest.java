package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final Operation PURCHASE = Operation.PURCHASE;
    private OperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test(expected = RuntimeException.class)
    public void currentQuantityNull_NotOk() {
        Storage.fruits.put("orange", null);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setName("orange");
        fruitTransaction.setQuantity(25);
        fruitTransaction.setOperation(PURCHASE);
        purchaseOperationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void currentQuantityLessThanPurchase_NotOk() {
        Storage.fruits.put("orange", 5);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setName("orange");
        fruitTransaction.setQuantity(15);
        fruitTransaction.setOperation(PURCHASE);
        purchaseOperationHandler.handle(fruitTransaction);
    }

    @Test
    public void tenMinusPurchaseSixShouldEqualFour_Ok() {
        Storage.fruits.put("orange", 10);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setName("orange");
        fruitTransaction.setQuantity(6);
        fruitTransaction.setOperation(PURCHASE);
        purchaseOperationHandler.handle(fruitTransaction);
        Integer expectedQuantity = 4;
        Integer actualQuantity = Storage.fruits.get("orange");
        assertEquals("Test failed! Expected quantity should be " + expectedQuantity + " but it is "
                        + actualQuantity, expectedQuantity, actualQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
