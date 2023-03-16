package strategy.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strategy.TransactionHandler;

public class PurchaseHandlerTest {
    private static TransactionHandler purchaseHandler;
    private static FruitTransaction purchase;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandler();

        purchase = new FruitTransaction();
        purchase.setOperation(FruitTransaction.Operation.PURCHASE);
        purchase.setFruit("banana");
        purchase.setQuantity(5);

        Storage.fruits.put("banana", 10);
    }

    @Test
    public void handle_PurchaseSuccessful_Ok() {
        Integer expected = 5;
        purchaseHandler.handle(purchase);
        Integer actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_PurchaseNotSuccessful_NotOk() {
        purchase.setQuantity(20);
        purchaseHandler.handle(purchase);
        fail(RuntimeException.class.getName());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
