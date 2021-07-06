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

public class AddOperationHandlerTest {
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
    public void supplySingle() {
        int expected = 50;
        Transaction transaction = new Transaction(SUPPLY, APPLE, 30);
        operationStrategyMap.get(transaction.getOperation()).apply(transaction);
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @Test
    public void supplyMultiple() {
        final int expectedApple = 80;
        final int expectedBanana = 60;
        final int expectedOrange = 70;

        Transaction transactionApple = new Transaction(SUPPLY, APPLE, 30);
        Transaction transactionOrange = new Transaction(SUPPLY, ORANGE, 30);
        Transaction transactionBanana = new Transaction(SUPPLY, BANANA, 30);

        operationStrategyMap.get(transactionApple.getOperation()).apply(transactionApple);
        operationStrategyMap.get(transactionApple.getOperation()).apply(transactionApple);
        operationStrategyMap.get(transactionOrange.getOperation()).apply(transactionOrange);
        operationStrategyMap.get(transactionBanana.getOperation()).apply(transactionBanana);

        assertEquals(expectedApple, Storage.storage.get(APPLE).intValue());
        assertEquals(expectedBanana, Storage.storage.get(BANANA).intValue());
        assertEquals(expectedOrange, Storage.storage.get(ORANGE).intValue());
    }

    @Test
    public void returnSingle() {
        int expected = 50;
        Transaction transaction = new Transaction(RETURN, APPLE, 30);
        operationStrategyMap.get(transaction.getOperation()).apply(transaction);
        assertEquals(expected, Storage.storage.get(APPLE).intValue());
    }

    @Test
    public void returnMultiple() {
        final int expectedApple = 80;
        final int expectedBanana = 60;
        final int expectedOrange = 70;

        Transaction transactionApple = new Transaction(RETURN, APPLE, 30);
        Transaction transactionOrange = new Transaction(RETURN, ORANGE, 30);
        Transaction transactionBanana = new Transaction(RETURN, BANANA, 30);

        operationStrategyMap.get(transactionApple.getOperation()).apply(transactionApple);
        operationStrategyMap.get(transactionApple.getOperation()).apply(transactionApple);
        operationStrategyMap.get(transactionOrange.getOperation()).apply(transactionOrange);
        operationStrategyMap.get(transactionBanana.getOperation()).apply(transactionBanana);

        assertEquals(expectedApple, Storage.storage.get(APPLE).intValue());
        assertEquals(expectedBanana, Storage.storage.get(BANANA).intValue());
        assertEquals(expectedOrange, Storage.storage.get(ORANGE).intValue());
    }

    @Test
    public void returnAndSupplyOperations() {
        final int expectedApple = 140;
        final int expectedBanana = 90;
        final int expectedOrange = 130;

        Transaction transactionReturnApple = new Transaction(RETURN, APPLE, 30);
        Transaction transactionReturnOrange = new Transaction(RETURN, ORANGE, 30);
        Transaction transactionReturnBanana = new Transaction(RETURN, BANANA, 30);

        Transaction transactionSupplyApple = new Transaction(SUPPLY, APPLE, 30);
        Transaction transactionSupplyOrange = new Transaction(SUPPLY, ORANGE, 30);
        Transaction transactionSupplyBanana = new Transaction(SUPPLY, BANANA, 30);

        operationStrategyMap
                .get(transactionReturnApple.getOperation()).apply(transactionReturnApple);
        operationStrategyMap
                .get(transactionReturnApple.getOperation()).apply(transactionReturnApple);
        operationStrategyMap
                .get(transactionReturnOrange.getOperation()).apply(transactionReturnOrange);
        operationStrategyMap
                .get(transactionReturnBanana.getOperation()).apply(transactionReturnBanana);
        operationStrategyMap
                .get(transactionSupplyApple.getOperation()).apply(transactionSupplyApple);
        operationStrategyMap
                .get(transactionSupplyApple.getOperation()).apply(transactionSupplyApple);
        operationStrategyMap
                .get(transactionSupplyBanana.getOperation()).apply(transactionSupplyBanana);
        operationStrategyMap
                .get(transactionSupplyOrange.getOperation()).apply(transactionSupplyOrange);
        operationStrategyMap
                .get(transactionSupplyOrange.getOperation()).apply(transactionSupplyOrange);

        assertEquals(expectedApple, Storage.storage.get(APPLE).intValue());
        assertEquals(expectedBanana, Storage.storage.get(BANANA).intValue());
        assertEquals(expectedOrange, Storage.storage.get(ORANGE).intValue());
    }
}
