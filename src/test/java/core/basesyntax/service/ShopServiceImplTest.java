package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private final String apple = "apple";

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(shopService));
    }

    @Test
    public void testAddFruits() {
        shopService.addFruits(apple, 50);
        assertEquals(50, shopService.getQuantity(apple),
                "The quantity of apples should be 50.");
    }

    @Test
    public void testPurchaseFruits() {
        shopService.addFruits(apple, 50);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                apple, 30);
        shopService.process(List.of(transaction));
        assertEquals(20, shopService.getQuantity(apple),
                "The quantity of apples should be 20 after purchase.");
    }

    @Test
    public void testPurchaseInsufficientQuantity() {
        shopService.addFruits(apple, 20);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                apple, 30);

        assertThrows(RuntimeException.class, () ->
                        shopService.process(List.of(transaction)),
                "Should throw exception when trying to purchase more than available.");
    }

    @Test
    public void testGetFruits() {
        shopService.addFruits(apple, 50);
        shopService.addFruits("banana", 30);

        Map<String, Integer> expectedInventory = new HashMap<>();
        expectedInventory.put(apple, 50);
        expectedInventory.put("banana", 30);

        assertEquals(expectedInventory, shopService.getFruits(),
                "The inventory should match the expected values.");
    }

}
