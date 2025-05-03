package core.basesyntax.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTest {
    private static final String PURCHASE = "p";
    private static final String APPLE = "apple";
    private static final String PINEAPPLE = "pineapple";
    private static OperationHandler handler;
    private static Transaction transactionApple;
    private static Transaction transactionBanana;
    private static Fruit apple;
    private static Fruit pineapple;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperationHandler();
        apple = new Fruit(APPLE);
        pineapple = new Fruit(PINEAPPLE);
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.put(apple, 50);
        Storage.fruitStorage.put(pineapple, 50);
    }

    @Test
    public void purchase_addTransaction_Ok() {
        transactionApple = new Transaction(PURCHASE, APPLE, 10);
        transactionBanana = new Transaction(PURCHASE, PINEAPPLE, 40);

        handler.apply(transactionApple);
        handler.apply(transactionBanana);

        int actualApple = Storage.fruitStorage.get(apple);
        int actualBanana = Storage.fruitStorage.get(pineapple);
        int expectedApple = 40;
        int expectedBanana = 10;

        Assert.assertEquals(expectedApple, actualApple);
        Assert.assertEquals(expectedBanana, actualBanana);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_addTransactionWithNotEnough_notOk() {
        transactionApple = new Transaction(PURCHASE, APPLE, 450);
        transactionBanana = new Transaction(PURCHASE, PINEAPPLE, 780);

        handler.apply(transactionApple);
        handler.apply(transactionBanana);
    }

    @AfterClass
    public static void tearDown() {
        Storage.fruitStorage.clear();
    }
}
