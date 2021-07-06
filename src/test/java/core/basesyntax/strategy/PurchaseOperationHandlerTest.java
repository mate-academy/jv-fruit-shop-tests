package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String PURCHASE = "p";
    private static final String SUPPLY = "s";
    private static final String BALANCE = "b";
    private static final String RETURN = "r";
    private static final Fruit APPLE = new Fruit("apple");
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit ORANGE = new Fruit("orange");
    private static Map<String, OperationHandler> operationStrategyMap;

    @BeforeClass
    public static void before() {
        operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(PURCHASE, new PurchaseOperationHandler());
        operationStrategyMap.put(SUPPLY, new AddOperationHandler());
        operationStrategyMap.put(BALANCE, new BalanceOperationHandler());
        operationStrategyMap.put(RETURN, new AddOperationHandler());
    }

    @Before
    public void beforeEach() {
        Storage.storage.clear();
        Storage.storage.put(APPLE, 20);
        Storage.storage.put(BANANA, 30);
        Storage.storage.put(ORANGE, 40);
    }

    @Test
    public void purchaseBasic() {
        int expectedApple = 10;
        int expectedBanana = 25;
        int expectedOrange = 10;

        Transaction transactionApple = new Transaction(PURCHASE, APPLE, 10);
        Transaction transactionBanana = new Transaction(PURCHASE, BANANA, 5);
        Transaction transactionOrange = new Transaction(PURCHASE, ORANGE, 30);

        int actualApple =
                operationStrategyMap.get(transactionApple.getOperation())
                        .apply(transactionApple);
        int actualBanana =
                operationStrategyMap.get(transactionBanana.getOperation())
                        .apply(transactionBanana);
        int actualOrange =
                operationStrategyMap.get(transactionOrange.getOperation())
                        .apply(transactionOrange);

        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }

    @Test
    public void purchaseMoreThanAvailable() {
        int expectedApple = 0;
        int expectedBanana = 0;
        int expectedOrange = 10;

        Transaction transactionApple = new Transaction(PURCHASE, APPLE, 10000);
        Transaction transactionBanana = new Transaction(PURCHASE, BANANA, 5342);
        Transaction transactionOrange = new Transaction(PURCHASE, ORANGE, 30);

        int actualApple =
                operationStrategyMap.get(transactionApple.getOperation())
                        .apply(transactionApple);
        int actualBanana =
                operationStrategyMap.get(transactionBanana.getOperation())
                        .apply(transactionBanana);
        int actualOrange =
                operationStrategyMap.get(transactionOrange.getOperation())
                        .apply(transactionOrange);

        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }
}
