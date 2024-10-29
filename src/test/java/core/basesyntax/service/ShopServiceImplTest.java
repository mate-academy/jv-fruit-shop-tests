package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.transaction.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler(shopService));
    }

    @Test
    public void testAddFruits() {
        shopService.addFruits("apple", 50);
        assertEquals(50, shopService.getQuantity("apple"),
                "The quantity of apples should be 50.");
    }

    @Test
    public void testSupplyFruits() {
        shopService.addFruits("apple", 50);
        shopService.supplyFruits("apple", 20);
        assertEquals(70, shopService.getQuantity("apple"),
                "The quantity of apples should be 70 after supply.");
    }

    @Test
    public void testPurchaseFruits() {
        shopService.addFruits("apple", 50);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 30);
        shopService.process(List.of(transaction));
        assertEquals(20, shopService.getQuantity("apple"),
                "The quantity of apples should be 20 after purchase.");
    }

    @Test
    public void testPurchaseInsufficientQuantity() {
        shopService.addFruits("apple", 20);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 30);

        assertThrows(RuntimeException.class, () ->
                        shopService.process(List.of(transaction)),
                "Should throw exception when trying to purchase more than available.");
    }

    @Test
    public void testGetFruits() {
        shopService.addFruits("apple", 50);
        shopService.addFruits("banana", 30);

        Map<String, Integer> expectedInventory = new HashMap<>();
        expectedInventory.put("apple", 50);
        expectedInventory.put("banana", 30);

        assertEquals(expectedInventory, shopService.getFruits(),
                "The inventory should match the expected values.");
    }
}
