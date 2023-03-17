package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operations.BalanceOperationHandler;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.PurchaseOperationHandler;
import core.basesyntax.strategy.operations.ReturnOperationHandler;
import core.basesyntax.strategy.operations.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionHandlerImplTest {
    private static final String TITLE_FOR_BANANAS = "banana";
    private static final String TITLE_FOR_APPLES = "apple";
    private static final Integer QUANTITY_FOR_BANANAS = 152;
    private static final Integer QUANTITY_FOR_APPLES = 90;
    private static final List<FruitTransaction> EMPTY_FRUIT_TRANSACTIONS = Collections.emptyList();
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = new HashMap<>();
    private static List<FruitTransaction> validFruitTransactions;
    private static TransactionHandler transactionHandler;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionHandler = new TransactionHandlerImpl(operationStrategy);
        validFruitTransactions = new ArrayList<>();
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                TITLE_FOR_BANANAS, 20));
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                TITLE_FOR_APPLES, 100));
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                TITLE_FOR_BANANAS, 100));
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                TITLE_FOR_BANANAS, 13));
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                TITLE_FOR_APPLES, 10));
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                TITLE_FOR_APPLES, 20));
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                TITLE_FOR_BANANAS, 5));
        validFruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                TITLE_FOR_BANANAS, 50));
    }

    @Test (expected = RuntimeException.class)
      public void processData_emptyListOfData_notOk() {
        transactionHandler.processData(EMPTY_FRUIT_TRANSACTIONS);
        fail("You must throw Runtime Exception, if the input list is empty");
    }

    @Test
    public void processData_validListOfData_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(TITLE_FOR_BANANAS, QUANTITY_FOR_BANANAS);
        expected.put(TITLE_FOR_APPLES, QUANTITY_FOR_APPLES);
        transactionHandler.processData(validFruitTransactions);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
