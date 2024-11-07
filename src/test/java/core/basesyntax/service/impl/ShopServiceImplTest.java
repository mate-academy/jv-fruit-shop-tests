package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.stategy.BalanceHandler;
import core.basesyntax.stategy.FruitOperationHandler;
import core.basesyntax.stategy.OperationStrategy;
import core.basesyntax.stategy.OperationStrategyImpl;
import core.basesyntax.stategy.PurchaseOperation;
import core.basesyntax.stategy.ReturnOperation;
import core.basesyntax.stategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Map<String, Integer> inventoryData;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        inventoryData = new HashMap<>();
        Map<Operation, FruitOperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void processMultipleTransaction_OK() {
        inventoryData.put("apple", 20);
        inventoryData.put("banana", 10);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.PURCHASE, "banana", 10));
        transactions.add(new FruitTransaction(Operation.RETURN,"banana", 5));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "grapes",100));
        shopService.processTransaction(transactions, inventoryData);
        assertEquals(20, inventoryData.get("apple"));
        assertEquals(5, inventoryData.get("banana"));
        assertEquals(100,inventoryData.get("grapes"));
    }

    @Test
    void processBalanceOperation_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        shopService.processTransaction(List.of(fruitTransaction), inventoryData);
        assertEquals(10, inventoryData.get("apple"));
    }

    @Test
    void processSupplyOperation_OK() {
        inventoryData.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "apple", 5);
        shopService.processTransaction(List.of(fruitTransaction), inventoryData);
        assertEquals(15, inventoryData.get("apple"));
    }

    @Test
    void processPurchaseOperation_OK() {
        inventoryData.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 5);
        shopService.processTransaction(List.of(fruitTransaction), inventoryData);
        assertEquals(5, inventoryData.get("apple"));
    }

    @Test
    void processReturnOperation_OK() {
        inventoryData.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "apple", 15);
        shopService.processTransaction(List.of(fruitTransaction), inventoryData);
        assertEquals(25, inventoryData.get("apple"));
    }

    @Test
    void processOperation_NoHandler_trowsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(null, "apple", 10);
        assertThrows(IllegalArgumentException.class, () ->
                shopService.processTransaction(List.of(fruitTransaction), inventoryData));
    }
}
