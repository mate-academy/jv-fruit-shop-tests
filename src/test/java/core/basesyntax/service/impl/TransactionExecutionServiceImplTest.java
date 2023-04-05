package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TransactionExecutionServiceImplTest {
    private static List<FruitTransaction> fruitTransactionList;
    private static Map<FruitTransaction.Operation, OperationHandler> handlersMap;
    private static Map<String, Integer> expectedFruitStorage;
    private static TransactionExecutionServiceImpl transactionExecutionService;
    private static OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        handlersMap = new HashMap<>();
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlersMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20));
        operationStrategy = new OperationStrategyImpl(handlersMap);
        transactionExecutionService = new TransactionExecutionServiceImpl();
        transactionExecutionService.executeTransaction(fruitTransactionList, operationStrategy);
        expectedFruitStorage = new HashMap<>();
        expectedFruitStorage.put("banana",110);
        expectedFruitStorage.put("apple",20);
    }

    @Test
    public void executeTransaction_validFruitTransactionList_ok() {
        assertEquals(expectedFruitStorage, FruitStorage.fruitStorage);
    }
}
