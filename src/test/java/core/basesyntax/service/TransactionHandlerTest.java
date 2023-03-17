package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.Utils;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionHandlerImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.BuyOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static TransactionHandler transactionHandler;
    private static List<FruitTransaction> input;

    @BeforeClass
    public static void beforeAll() {
        Storage.fruits.clear();
        input = new ArrayList<>();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new BuyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        input.clear();
        input.add(Utils.createTransaction(FruitTransaction.Operation.BALANCE, BANANA, 200));
        input.add(Utils.createTransaction(FruitTransaction.Operation.RETURN, APPLE, 100));
        input.add(Utils.createTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 50));
        input.add(Utils.createTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50));
        transactionHandler = new TransactionHandlerImpl(operationStrategy);
    }

    @Test
    public void transactionHandler_parse_Ok() {
        transactionHandler.parse(input);
        int expectedValue = 250;
        int actualValue = Storage.fruits.get(BANANA);
        assertEquals(expectedValue, actualValue);
        expectedValue = 50;
        actualValue = Storage.fruits.get(APPLE);
        assertEquals(expectedValue, actualValue);
    }

    @Test(expected = RuntimeException.class)
    public void transactionHandler_notFoundFruit_NotOk() {
        transactionHandler.parse(null);
    }

    @Test
    public void transactionHandler_parseEmptyInput_Ok() {
        transactionHandler.parse(new ArrayList<>());
        assertTrue(Storage.fruits.isEmpty());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}
