package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandler;
import core.basesyntax.service.operation.ReturnHandler;
import core.basesyntax.service.operation.SupplierHandler;
import core.basesyntax.serviceimpl.FruitServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceTest {
    private static FruitServiceImpl fruitService;
    private static OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplierHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationStrategy = new OperationStrategy.OperationStrategyImpl(operationHandlerMap);
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @Test
    public void calculateBalanceTest_Calculate_OK() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",20));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple",10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",120));
        Map<String, String> actual = fruitService.calculateBalance(transactions);
        Map<String, String> expected = new HashMap<>();
        expected.put("apple","30");
        expected.put("banana","120");
        assertEquals(expected, actual);
    }

    @Test
    public void calculateBalance_EmptyData_OK() {
        List<FruitTransaction> transactions = new ArrayList<>();
        Map<String, String> actual = fruitService.calculateBalance(transactions);
        Map<String, String> expected = new HashMap<>();
        assertEquals(expected, actual);
    }
}
