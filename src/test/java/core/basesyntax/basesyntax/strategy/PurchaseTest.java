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
    private static final String YABLOKO = "yabloko";
    private static final String BANANCHIK = "bananchik";
    private static OperationHandler handler;
    private static Transaction transactionApple;
    private static Transaction transactionBanana;
    private static Fruit yabloko;
    private static Fruit bananchik;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperationHandler();
        yabloko = new Fruit(YABLOKO);
        bananchik = new Fruit(BANANCHIK);
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.put(yabloko, 50);
        Storage.fruitStorage.put(bananchik, 50);
    }

    @Test
    public void purchase_addTransaction_Ok() {
        transactionApple = new Transaction(PURCHASE, YABLOKO, 10);
        transactionBanana = new Transaction(PURCHASE, BANANCHIK, 40);

        handler.apply(transactionApple);
        handler.apply(transactionBanana);

        int actualApple = Storage.fruitStorage.get(yabloko);
        int actualBanana = Storage.fruitStorage.get(bananchik);
        int expectedApple = 40;
        int expectedBanana = 10;

        Assert.assertEquals(expectedApple, actualApple);
        Assert.assertEquals(expectedBanana, actualBanana);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_addTransactionWithNotEnough_notOk() {
        transactionApple = new Transaction(PURCHASE, YABLOKO, 450);
        transactionBanana = new Transaction(PURCHASE, BANANCHIK, 780);

        handler.apply(transactionApple);
        handler.apply(transactionBanana);
    }

    @AfterClass
    public static void tearDown() {
        Storage.fruitStorage.clear();
    }
}
