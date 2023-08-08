package core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.exception.OperationHandlerException;
import core.operationstrategy.OperationStrategyImpl;
import core.transactions.BalanceOperationHandler;
import core.transactions.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitStoreTest {
    private Map<OperationType, OperationHandler> operationHandlers;
    private OperationStrategyImpl operationStrategy;
    private FruitStore fruitStore;

    @BeforeEach
    public void setUp() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(OperationType.B, new BalanceOperationHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
        fruitStore = new FruitStore(operationStrategy);
        fruitStore.resetTransactions();
    }

    @Test
    public void testProcessOperations_ValidData_ok() {
        List<OperationData> testData = new ArrayList<>();
        testData.add(new OperationData(OperationType.B, "Apple", 200));

        List<OperationData> result = fruitStore.processOperations(testData);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(200, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_NullDataList_notOk() {
        assertThrows(RuntimeException.class, () ->
                fruitStore.processOperations(null));
    }

    @Test
    public void testProcessOperations_InvalidOperationType_notOk() {
        List<OperationData> testData = new ArrayList<>();
        testData.add(new OperationData(OperationType.P, "Apple", 200));

        assertThrows(OperationHandlerException.class, () ->
                fruitStore.processOperations(testData));
    }
}