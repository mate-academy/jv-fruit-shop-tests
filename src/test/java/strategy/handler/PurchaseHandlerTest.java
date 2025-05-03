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
    private static final String FRUIT_FOR_TEST = "banana";
    private static final Integer QUANTITY_IN_STORAGE = 10;
    private static final Integer QUANTITY_OF_FRUIT_AFTER_PURCHASE = 6;
    private static final Integer QUANTITY_FOR_ENOUGH_SELL = 4;
    private static final Integer QUANTITY_FOR_NOT_ENOUGH_SELL = 20;
    private static TransactionHandler purchaseHandler;
    private static FruitTransaction purchase;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandler();
        purchase = new FruitTransaction();
        purchase.setOperation(FruitTransaction.Operation.PURCHASE);
        purchase.setFruit(FRUIT_FOR_TEST);
        purchase.setQuantity(QUANTITY_FOR_ENOUGH_SELL);

        Storage.fruits.put(FRUIT_FOR_TEST, QUANTITY_IN_STORAGE);
    }

    @Test
    public void handle_PurchaseSuccessful_Ok() {
        Integer expected = QUANTITY_OF_FRUIT_AFTER_PURCHASE;
        purchaseHandler.handle(purchase);
        Integer actual = Storage.fruits.get(FRUIT_FOR_TEST);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_PurchaseNotSuccessful_NotOk() {
        purchase.setQuantity(QUANTITY_FOR_NOT_ENOUGH_SELL);
        purchaseHandler.handle(purchase);
        fail(RuntimeException.class.getName());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
