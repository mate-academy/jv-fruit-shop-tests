package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionHandlerImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionHandlerServiceTest {
    private static Map<FruitTransaction.OperationType, OperationHandler> handlerMap;
    private static TransactionHandlerService transactionHandler;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = Map.of(
                FruitTransaction.OperationType.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.OperationType.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.OperationType.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.OperationType.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(handlerMap);
        transactionHandler = new TransactionHandlerImpl(operationStrategy);
    }

    @Test
    public void handleTransaction_validData_ok() {
        List<FruitTransaction> transactions = fillExpectedList();
        Map<String, Integer> expectedMap = fillExpectedMap();
        transactionHandler.handleTransactions(transactions);
        Assert.assertEquals(expectedMap.size(), Storage.fruits.size());

        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            Assert.assertEquals(expectedMap.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void handleTransaction_emptyData_ok() {
        List<FruitTransaction> transactions = Collections.emptyList();
        Map<String, Integer> expectedMap = Collections.emptyMap();
        transactionHandler.handleTransactions(transactions);
        Assert.assertEquals(expectedMap.size(), Storage.fruits.size());
    }

    @Test(expected = NullPointerException.class)
    public void handleTransaction_nullData_notOk() {
        transactionHandler.handleTransactions(null);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    private Map<String, Integer> fillExpectedMap() {
        Map<String, Integer> expectedMap = Map.of(
                "banana", 152,
                "apple", 90
        );
        return expectedMap;
    }

    private List<FruitTransaction> fillExpectedList() {
        List<FruitTransaction> transactionsExpected = List.of(
                new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "banana", 20),
                new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "apple", 100),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY,
                        "banana", 100),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                        "banana", 13),
                new FruitTransaction(FruitTransaction.OperationType.RETURN,
                        "apple", 10),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                        "apple", 20),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                        "banana", 5),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY,
                        "banana", 50)
        );
        return transactionsExpected;
    }
}
