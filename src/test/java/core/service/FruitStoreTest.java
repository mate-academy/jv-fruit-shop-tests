package core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.exception.OperationHandlerException;
import core.operationstrategy.OperationStrategyImpl;
import core.transactions.BalanceOperationHandler;
import core.transactions.OperationHandler;
import core.transactions.PurchaseOperationHandler;
import core.transactions.ReturnOperationHandler;
import core.transactions.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitStoreTest {
    private Map<OperationType, OperationHandler> strategyMap = new HashMap<>();
    private OperationStrategyImpl strategy = new OperationStrategyImpl(strategyMap);
    private FruitStore fruitStore;

    @BeforeEach
    public void setUp() {
        strategyMap.put(OperationType.B, new BalanceOperationHandler());
        strategyMap.put(OperationType.P, new PurchaseOperationHandler());
        strategyMap.put(OperationType.S, new SupplyOperationHandler());
        strategyMap.put(OperationType.R, new ReturnOperationHandler());

        fruitStore = new FruitStore(strategy);
        fruitStore.resetTransactions();
    }

    @Test
    public void testProcessOperations_ValidData() {
        String data = "b,Apple,200" + System.lineSeparator()
                + "b,Orange,300" + System.lineSeparator()
                + "p,Apple,100" + System.lineSeparator()
                + "s,Orange,50";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        assertEquals(4, dataList.size());

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(2, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(100, result.get(0).getQuantity());
        assertEquals(OperationType.B, result.get(1).getOperationType());
        assertEquals("Orange", result.get(1).getProduct());
        assertEquals(350, result.get(1).getQuantity());
    }

    @Test
    public void testProcessOperations_EmptyDataList() {
        List<OperationData> dataList = new ArrayList<>();

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessOperations_NullDataList() {
        assertThrows(RuntimeException.class, () ->
                fruitStore.processOperations(null));
    }

    @Test
    public void testProcessOperations_InvalidOperationType() {
        String data = "x,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessOperations_NegativeBalance() {
        String data = "b,Apple,-200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        assertThrows(OperationHandlerException.class, () -> fruitStore.processOperations(dataList));
    }

    @Test
    public void testProcessOperations_PurchaseSuccess() {
        String data = "b,Apple,200" + System.lineSeparator() + "p,Apple,100";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_PurchaseInsufficientBalance() {
        String data = "b,Apple,200" + System.lineSeparator() + "p,Apple,300";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        assertThrows(OperationHandlerException.class, () -> fruitStore.processOperations(dataList));
    }

    @Test
    public void testProcessOperations_ReturnSuccess() {
        String data = "b,Apple,200" + System.lineSeparator() + "r,Apple,50";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(250, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_EmptyDataString() {
        String data = "";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertTrue(result.isEmpty());
    }
}
