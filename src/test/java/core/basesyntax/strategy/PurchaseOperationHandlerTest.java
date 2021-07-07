package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String PURCHASE = "p";
    private static final Fruit APPLE = new Fruit("apple");
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit ORANGE = new Fruit("orange");
    private static OperationHandler purchaseOperation;

    @BeforeClass
    public static void before() {
        purchaseOperation = new PurchaseOperationHandler();
    }

    @Before
    public void beforeEach() {
        Storage.storage.clear();
        Storage.storage.put(APPLE, 20);
        Storage.storage.put(BANANA, 30);
        Storage.storage.put(ORANGE, 40);
    }

    @Test
    public void purchaseBasic_Ok() {
        int expectedApple = 10;
        int expectedBanana = 25;
        int expectedOrange = 10;

        Transaction transactionApple = new Transaction(PURCHASE, APPLE, 10);
        Transaction transactionBanana = new Transaction(PURCHASE, BANANA, 5);
        Transaction transactionOrange = new Transaction(PURCHASE, ORANGE, 30);

        int actualApple = purchaseOperation.apply(transactionApple);
        int actualBanana = purchaseOperation.apply(transactionBanana);
        int actualOrange = purchaseOperation.apply(transactionOrange);

        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }

    @Test
    public void purchaseMoreThanAvailable_Ok() {
        int expectedApple = 0;
        int expectedBanana = 0;
        int expectedOrange = 10;

        Transaction transactionApple = new Transaction(PURCHASE, APPLE, 10000);
        Transaction transactionBanana = new Transaction(PURCHASE, BANANA, 5342);
        Transaction transactionOrange = new Transaction(PURCHASE, ORANGE, 30);

        int actualApple = purchaseOperation.apply(transactionApple);
        int actualBanana = purchaseOperation.apply(transactionBanana);
        int actualOrange = purchaseOperation.apply(transactionOrange);

        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }
}
