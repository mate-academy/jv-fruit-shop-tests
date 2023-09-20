package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static Map<OperationType, OperationHandler> operationHandlerMap;
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @BeforeEach
    void setUp() {
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperation());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperation());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperation());
    }

    @Test
    void getOperationHandler_ValidData_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                OperationType.BALANCE, "banana", 10);
        assertEquals(BalanceOperation.class, strategy.getOperationHandler(
                fruitTransaction).getClass());
        FruitTransaction fruitTransaction1 = new FruitTransaction(
                OperationType.SUPPLY, "banana", 10);
        assertEquals(SupplyOperation.class, strategy.getOperationHandler(
                fruitTransaction1).getClass());
        FruitTransaction fruitTransaction2 = new FruitTransaction(
                OperationType.PURCHASE, "banana", 10);
        assertEquals(PurchaseOperation.class, strategy.getOperationHandler(
                fruitTransaction2).getClass());
        FruitTransaction fruitTransaction3 = new FruitTransaction(
                OperationType.RETURN, "banana", 10);
        assertEquals(ReturnOperation.class, strategy.getOperationHandler(
                fruitTransaction3).getClass());
    }
}
