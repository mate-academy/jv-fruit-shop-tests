package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 100;

    private static TransactionProcessor transactionProcessor;
    private List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
    }

    @Before
    public void setUp() {
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(Operation.BALANCE, FRUIT, QUANTITY));
        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY, FRUIT, QUANTITY));
        fruitTransactions.add(new FruitTransaction(Operation.PURCHASE, FRUIT, QUANTITY));
        fruitTransactions.add(new FruitTransaction(Operation.RETURN, FRUIT, QUANTITY));
    }

    @Test
    public void processValidData_ok() {
        Integer expectedQuantity = 100 + 100 - 100 + 100;
        Map<String, Integer> actualProcessMap = transactionProcessor.process(fruitTransactions);
        Integer actualQuantity = actualProcessMap.get(FRUIT);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void summingIntegerMaxValue_notOk() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.BALANCE, FRUIT, Integer.MAX_VALUE);
        fruitTransactions.add(fruitTransaction);
        Map<String, Integer> processMap = transactionProcessor.process(fruitTransactions);
        Integer expectedQuantity = 100 + 100 - 100 + 100;
        Integer actualQuantity = processMap.get(FRUIT);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @After
    public void tearDown() {
        fruitTransactions.clear();
    }
}
