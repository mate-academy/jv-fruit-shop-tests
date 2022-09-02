package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final Fruit TEST_FRUIT = new Fruit("banana");
    private static final int EMPTY_QUANTITY = 0;
    private static final String BALANCE_OPERATION = "b";
    private static final String PURCHASE_OPERATION = "p";
    private static final String SUPPLY_OPERATION = "s";
    private static final String RETURN_OPERATION = "r";
    private static Map<String, OperationHandler> Map;
    private List<Transaction> transactions;

    @BeforeClass
    public static void beforeClass() {
        Map = new HashMap<>();
        Map.put(BALANCE_OPERATION, new BalanceOperationHandler());
        Map.put(SUPPLY_OPERATION, new SupplyOperationHandler());
        Map.put(PURCHASE_OPERATION, new PurchaseOperationHandler());
        Map.put(RETURN_OPERATION, new ReturnOperationHandler());
    }

    @Before
    public void setUp() {
        Storage.storage.put(TEST_FRUIT, EMPTY_QUANTITY);
        transactions = new ArrayList<>();
    }

    @Test
    public void handleAllOperations_OK() {
        OperationStrategy strategy = new OperationStrategy(Map);
        Transaction balanceTransaction = new Transaction(BALANCE_OPERATION,
                TEST_FRUIT, 20);
        Transaction supplyTransaction = new Transaction(SUPPLY_OPERATION,
                TEST_FRUIT, 15);
        Transaction purchaseTransaction = new Transaction(PURCHASE_OPERATION,
                TEST_FRUIT, 30);
        Transaction returnTransaction = new Transaction(RETURN_OPERATION,
                TEST_FRUIT, 10);
        transactions.add(balanceTransaction);
        transactions.add(supplyTransaction);
        transactions.add(purchaseTransaction);
        transactions.add(returnTransaction);

        for (Transaction transaction : transactions) {
            OperationHandler handler = strategy.getByOperation(transaction.getOperation());
            handler.apply(transaction);
        }

        int expected = 15;
        int actual = Storage.storage.get(TEST_FRUIT);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
        transactions.clear();
    }
}
