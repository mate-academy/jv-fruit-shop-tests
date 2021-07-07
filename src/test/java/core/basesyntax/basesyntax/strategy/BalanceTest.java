package core.basesyntax.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceTest {
    private static final String BALANCE = "b";
    private static final String APPLE = "apple";
    private static final String PINEAPPLE = "pineapple";
    private static final String VODKA = "vodka";
    private static OperationHandler handler;
    private static Fruit apple;
    private static Fruit pineapple;
    private static Fruit vodka;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
        apple = new Fruit(APPLE);
        pineapple = new Fruit(PINEAPPLE);
        vodka = new Fruit(VODKA);
    }

    @Test
    public void balance_addTransactionExistFruit_Ok() {
        Transaction transactionApple = new Transaction(BALANCE, APPLE, 200);
        Transaction transactionBanana = new Transaction(BALANCE, PINEAPPLE, 100);
        handler.apply(transactionApple);
        handler.apply(transactionBanana);

        int actualApple = Storage.fruitStorage.get(apple);
        int actualBanana = Storage.fruitStorage.get(pineapple);

        int expectedApple = 200;
        int expectedBanana = 100;

        Assert.assertEquals(expectedApple, actualApple);
        Assert.assertEquals(expectedBanana, actualBanana);
    }

    @Test
    public void balance_addTransactionNotExistFruit_Ok() {
        Transaction transactionVodka = new Transaction(BALANCE, VODKA, 15);
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
