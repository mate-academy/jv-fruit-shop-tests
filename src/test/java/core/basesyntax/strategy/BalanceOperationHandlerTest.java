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

public class BalanceOperationHandlerTest {
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
    }

    @Test
    public void balanceOperationBasic() {
        int expectedApple = 20;
        int expectedBanana = 40;
        int expectedOrange = 30;

        Transaction transactionApple = new Transaction(BALANCE, APPLE, 20);
        Transaction transactionOrange = new Transaction(BALANCE, ORANGE, 30);
        Transaction transactionBanana = new Transaction(BALANCE, BANANA, 40);

        int actualApple = operationStrategyMap.get(transactionApple.getOperation())
                .apply(transactionApple);
        int actualBanana = operationStrategyMap.get(transactionBanana.getOperation())
                .apply(transactionBanana);
        int actualOrange = operationStrategyMap.get(transactionOrange.getOperation())
                .apply(transactionOrange);

        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }
}
