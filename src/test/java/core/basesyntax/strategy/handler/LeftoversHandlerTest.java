package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LeftoversHandlerTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation PURCHASE = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static OperationStrategy operationStrategy;
    private static LeftoversHandler leftoversHandler;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        leftoversHandler = new LeftoversHandlerImpl(operationStrategy);
        fruitTransactionList = new ArrayList<>();
    }

    @Before
    public void setUp() {
        fruitTransactionList.add(new FruitTransaction(BALANCE, BANANA, 20));
        fruitTransactionList.add(new FruitTransaction(BALANCE, APPLE, 100));
        fruitTransactionList.add(new FruitTransaction(SUPPLY, BANANA, 100));
        fruitTransactionList.add(new FruitTransaction(PURCHASE, BANANA, 13));
        fruitTransactionList.add(new FruitTransaction(RETURN, APPLE, 10));
        fruitTransactionList.add(new FruitTransaction(PURCHASE, APPLE, 20));
        fruitTransactionList.add(new FruitTransaction(PURCHASE, BANANA, 5));
        fruitTransactionList.add(new FruitTransaction(SUPPLY, BANANA, 50));
    }

    @Test
    public void getLeftovers_validData_ok() {
        String expected = "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        String actual = leftoversHandler.getLeftovers(fruitTransactionList);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getLeftovers_negativeQuantity_notOk() {
        fruitTransactionList.add(new FruitTransaction(PURCHASE, BANANA, 500));
        leftoversHandler.getLeftovers(fruitTransactionList);
    }

    @Test
    public void getLeftovers_withNewFruits_ok() {
        fruitTransactionList.add(new FruitTransaction(BALANCE, "avocado", 100));
        fruitTransactionList.add(new FruitTransaction(PURCHASE, "avocado", 50));
        fruitTransactionList.add(new FruitTransaction(RETURN, "avocado", 25));
        fruitTransactionList.add(new FruitTransaction(BALANCE, "pineapple", 200));
        fruitTransactionList.add(new FruitTransaction(PURCHASE, "pineapple", 100));
        fruitTransactionList.add(new FruitTransaction(RETURN, "pineapple", 50));
        fruitTransactionList.add(new FruitTransaction(BALANCE, "watermelon", 300));
        fruitTransactionList.add(new FruitTransaction(PURCHASE, "watermelon", 150));
        fruitTransactionList.add(new FruitTransaction(RETURN, "watermelon", 75));
        String actual = leftoversHandler.getLeftovers(fruitTransactionList);
        String expected = "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator()
                + "avocado,75"
                + System.lineSeparator()
                + "pineapple,150"
                + System.lineSeparator()
                + "watermelon,225"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        fruitTransactionList.clear();
    }
}

