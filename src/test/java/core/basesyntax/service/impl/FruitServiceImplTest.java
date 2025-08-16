package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseHandler;
import core.basesyntax.strategy.operation.ReturnHandler;
import core.basesyntax.strategy.operation.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private List<FruitTransaction> fruitTransactions;
    private OperationStrategy operationStrategy;
    private FruitService fruitService;

    @BeforeEach
     void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitTransactions = new ArrayList<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @Test
    void validReport_Ok() {
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 15));
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10));
        Map<String, Integer> expected = Map.of("banana", 40);
        Map<String, Integer> actual = fruitService.process(fruitTransactions);
        assertEquals(expected, actual);
    }

    @Test
    void processNegativeQuantity_NotOk() {
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30));
        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> actual = fruitService.process(fruitTransactions);
        });
    }
}
