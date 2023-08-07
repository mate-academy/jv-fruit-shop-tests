package core.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void testProcessOperations_ValidData_ok() {
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
        String data = "b,Apple,200" + System.lineSeparator() + "p,Apple,100";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_PurchaseInsufficientBalance_notOk() {
        String data = "b,Apple,200" + System.lineSeparator() + "p,Apple,300";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        assertThrows(OperationHandlerException.class, () -> fruitStore.processOperations(dataList));
    }

    @Test
    public void testProcessOperations_ReturnSuccess_ok() {
        String data = "b,Apple,200" + System.lineSeparator() + "r,Apple,50";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(250, result.get(0).getQuantity());
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
        String data = "b,Apple,200" + System.lineSeparator() + "s,Apple,50";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(250, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_SupplyWithInvalidProduct_ok() {
        String data = "b,Apple,200" + System.lineSeparator() + "p,Apple,200";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testProcessOperations_ReturnInvalidProduct_ok() {
        String data = "b,Apple,200" + System.lineSeparator() + "r,Orange,50";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testProcessOperations_MultipleOperations_ok() {
        String data = "b,Apple,200" + System.lineSeparator()
                + "p,Apple,50" + System.lineSeparator()
                + "r,Apple,20" + System.lineSeparator()
                + "p,Apple,30" + System.lineSeparator()
                + "s,Apple,10";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(1, result.size());
        assertEquals(OperationType.B, result.get(0).getOperationType());
        assertEquals("Apple", result.get(0).getProduct());
        assertEquals(150, result.get(0).getQuantity());
    }

    @Test
    public void testProcessOperations_MultipleProducts_ok() {
        String data = "b,Apple,200" + System.lineSeparator()
                + "b,Orange,100" + System.lineSeparator()
                + "p,Apple,50" + System.lineSeparator()
                + "s,Orange,20" + System.lineSeparator()
                + "r,Apple,30";
        List<OperationData> dataList = new SplitDataImpl().splitData(data);

        List<OperationData> result = fruitStore.processOperations(dataList);

        assertEquals(2, result.size());

        OperationData appleBalance = result.stream()
                .filter(op -> op.getProduct().equals("Apple"))
                .findFirst()
                .orElse(null);
        assertNotNull(appleBalance);
        assertEquals(OperationType.B, appleBalance.getOperationType());
        assertEquals("Apple", appleBalance.getProduct());
        assertEquals(180, appleBalance.getQuantity());

        OperationData orangeBalance = result.stream()
                .filter(op -> op.getProduct().equals("Orange"))
                .findFirst()
                .orElse(null);
        assertNotNull(orangeBalance);
        assertEquals(OperationType.B, orangeBalance.getOperationType());
        assertEquals("Orange", orangeBalance.getProduct());
        assertEquals(120, orangeBalance.getQuantity());
    }
}
