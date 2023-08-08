package core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.exception.OperationHandlerException;
import core.operationstrategy.OperationStrategyImpl;
import core.transactions.BalanceOperationHandler;
import core.transactions.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitStoreTest {
    private FruitStore fruitStore;

    @BeforeEach
    public void setUp() {
        Map<OperationType, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(OperationType.B, new BalanceOperationHandler());
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(strategyMap);
        fruitStore = new FruitStore(operationStrategy);
        fruitStore.resetTransactions();
    }

    @Test
    public void testProcessOperations_ValidData_ok() {
        String data = "b,Apple,200" + System.lineSeparator()
                + "b,Orange,300";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        assertEquals(2, dataList.size());

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(2, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(200, result.get(0).getQuantity());
        assertEquals(OperationType.B, result.get(1).getOperationType());
        assertEquals("Orange", result.get(1).getProduct());
        assertEquals(300, result.get(1).getQuantity());
    }

    @Test
    public void testProcessOperations_EmptyDataList_ok() {
        List<OperationData> dataList = new ArrayList<>();

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessOperations_NullDataList_notOk() {
        assertThrows(RuntimeException.class, () ->
                fruitStore.processOperations(null));
    }

    @Test
    public void testProcessOperations_InvalidOperationType_ok() {
        String data = "x,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessOperations_NegativeBalance_notOk() {
        String data = "b,Apple,-200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        assertThrows(OperationHandlerException.class, () -> fruitStore.processOperations(dataList));
    }

    @Test
    public void testProcessOperations_PurchaseSuccess_ok() {
        String data = "b,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(200, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_PurchaseInsufficientBalance_notOk() {
        String data = "b,Apple,-200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        assertThrows(OperationHandlerException.class, () -> fruitStore.processOperations(dataList));
    }

    @Test
    public void testProcessOperations_ReturnSuccess_ok() {
        String data = "b,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(200, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_EmptyDataString_ok() {
        String data = "";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessOperations_SupplySuccess_ok() {
        String data = "b,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(200, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_SupplyWithInvalidProduct_ok() {
        String data = "b,Apple,400";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testProcessOperations_ReturnInvalidProduct_ok() {
        String data = "b,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testProcessOperations_MultipleOperations_ok() {
        String data = "b,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(200, result.get(0).getQuantity());
    }
}
