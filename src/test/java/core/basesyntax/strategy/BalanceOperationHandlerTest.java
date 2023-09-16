package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String BALANCE = "b";
    private static final Fruit APPLE = new Fruit("apple");
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit ORANGE = new Fruit("orange");
    private static OperationHandler balanceOperation;

    @BeforeClass
    public static void before() {
        balanceOperation = new BalanceOperationHandler();
    }

    @Before
    public void beforeEach() {
        Storage.storage.clear();
    }

    @Test
    public void balanceOperationBasic_Ok() {
        int expectedApple = 20;
        int expectedBanana = 40;
        int expectedOrange = 30;

        Transaction transactionApple = new Transaction(BALANCE, APPLE, 20);
        Transaction transactionOrange = new Transaction(BALANCE, ORANGE, 30);
        Transaction transactionBanana = new Transaction(BALANCE, BANANA, 40);

        int actualApple = balanceOperation.apply(transactionApple);
        int actualBanana = balanceOperation.apply(transactionBanana);
        int actualOrange = balanceOperation.apply(transactionOrange);

        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }
}
