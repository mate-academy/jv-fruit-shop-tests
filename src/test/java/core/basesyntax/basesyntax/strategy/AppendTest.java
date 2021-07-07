package core.basesyntax.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.AppendOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppendTest {
    private static final String APPEND = "s";
    private static final String RETURN = "r";
    private static final String YABLOKO = "yabloko";
    private static final String BANANCHIK = "bananchik";
    private static final String VODKA = "vodka";
    private static OperationHandler handler;
    private static Transaction transactionApple;
    private static Transaction transactionBanana;
    private static Fruit yabloko;
    private static Fruit bananchik;
    private static Fruit vodka;

    @BeforeClass
    public static void beforeClass() {
        handler = new AppendOperationHandler();
        yabloko = new Fruit(YABLOKO);
        bananchik = new Fruit(BANANCHIK);
        vodka = new Fruit(VODKA);
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.put(yabloko, 50);
        Storage.fruitStorage.put(bananchik, 50);
    }

    @Test
    public void append_addTransaction_Ok() {
        transactionApple = new Transaction(APPEND, YABLOKO, 88);
        transactionBanana = new Transaction(APPEND, BANANCHIK, 22);
        handler.apply(transactionApple);
        handler.apply(transactionBanana);

        int actualApple = Storage.fruitStorage.get(yabloko);
        int actualBanana = Storage.fruitStorage.get(bananchik);

        int expectedApple = 138;
        int expectedBanana = 72;

        Assert.assertEquals(expectedApple, actualApple);
        Assert.assertEquals(expectedBanana, actualBanana);
    }

    @Test
    public void append_addTransactionNotExistFruit_Ok() {
        Transaction transactionVodka = new Transaction(APPEND, VODKA, 15);
        handler.apply(transactionVodka);

        int actual = Storage.fruitStorage.get(vodka);
        int expected = 15;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void append_addTransactionReturn_Ok() {
        transactionApple = new Transaction(RETURN, YABLOKO, 88);
        transactionBanana = new Transaction(RETURN, BANANCHIK, 22);
        handler.apply(transactionApple);
        handler.apply(transactionBanana);

        int actualApple = Storage.fruitStorage.get(yabloko);
        int actualBanana = Storage.fruitStorage.get(bananchik);

        int expectedApple = 138;
        int expectedBanana = 72;

        Assert.assertEquals(expectedApple, actualApple);
        Assert.assertEquals(expectedBanana, actualBanana);
    }

    @Test
    public void append_addTransactionReturnNotExistFruit_Ok() {
        Transaction transactionVodka = new Transaction(RETURN, VODKA, 15);
        handler.apply(transactionVodka);

        int actual = Storage.fruitStorage.get(vodka);
        int expected = 15;

        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
