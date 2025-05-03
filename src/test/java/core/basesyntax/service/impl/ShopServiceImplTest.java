package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.FruitOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Map<Operation, FruitOperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;
    private Map<String, Integer> inventory;
    private ShopService shopService;

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    public void processMultipleFruitTransactions_processFruitTransactions_Ok() {
        inventory.put("apple", 20);
        inventory.put("banana", 5);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.PURCHASE, "apple", 5));
        transactions.add(new FruitTransaction(Operation.RETURN, "banana", 2));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "orange", 10));

        shopService.processFruitTransactions(transactions, inventory);

        Assertions.assertEquals(15, inventory.get("apple"));
        Assertions.assertEquals(7, inventory.get("banana"));
        Assertions.assertEquals(10, inventory.get("orange"));
    }

    @Test
    public void processBalanceFruitTransactions_processFruitTransactions_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        shopService.processFruitTransactions(List.of(transaction), inventory);
        Assertions.assertEquals(10, inventory.get("apple"));
    }

    @Test
    public void processPurchaseFruitTransactions_processFruitTransactions_Ok() {
        inventory.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 5);
        shopService.processFruitTransactions(List.of(transaction), inventory);
        Assertions.assertEquals(15, inventory.get("apple"));
    }

    @Test
    public void processReturnFruitTransactions_processFruitTransactions_Ok() {
        inventory.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 5);
        shopService.processFruitTransactions(List.of(transaction), inventory);
        Assertions.assertEquals(15, inventory.get("apple"));
    }

    @Test
    public void processSupplyFruitTransactions_processFruitTransactions_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 10);
        shopService.processFruitTransactions(List.of(transaction), inventory);
        Assertions.assertEquals(10, inventory.get("banana"));
    }

    @Test
    public void processFruitTransactions_NoHandler_shouldThrowException() {
        FruitTransaction transaction = new FruitTransaction(null, "banana", 10);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shopService.processFruitTransactions(List.of(transaction), inventory));
    }
}
