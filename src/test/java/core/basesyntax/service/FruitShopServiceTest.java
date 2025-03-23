package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyProvider;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceTest {
    private InventoryService inventoryService;
    private OperationStrategyProvider strategyProvider;
    private FruitShopService fruitShopService;

    private static class TestOperationHandler implements OperationHandler {
        private final InventoryService inventoryService;

        public TestOperationHandler(InventoryService inventoryService) {
            this.inventoryService = inventoryService;
        }

        @Override
        public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
            if (!inventory.containsKey(fruit)) {
                throw new IllegalArgumentException("Fruit not found: " + fruit);
            }
            // Додамо чи віднімемо кількість
            inventoryService.addFruit(fruit, quantity);
        }
    }

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        strategyProvider = new OperationStrategyProvider(inventoryService) {
            @Override
            public OperationHandler getHandler(FruitTransaction.OperationType operation) {
                return new TestOperationHandler(inventoryService);
            }
        };
        fruitShopService = new FruitShopService(inventoryService, strategyProvider);
    }

    @Test
    void processTransactions_emptyTransactionsList_success() {
        List<FruitTransaction> transactions = List.of();

        inventoryService.addFruit("apple", 10);
        inventoryService.addFruit("banana", 10);

        Assertions.assertDoesNotThrow(() -> fruitShopService.processTransactions(transactions));
        Assertions.assertEquals(10, inventoryService.getQuantity("apple"));
        Assertions.assertEquals(10, inventoryService.getQuantity("banana"));
    }

    @Test
    void processTransactions_invalidFruit_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.OperationType.ADD, "apple", 20),
                new FruitTransaction(FruitTransaction.OperationType.ADD, "orange", 10)
        );

        inventoryService.addFruit("apple", 10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fruitShopService.processTransactions(transactions));
    }

    @Test
    void processTransactions_negativeQuantity_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.OperationType.ADD, "apple", -5)
        );

        inventoryService.addFruit("apple", 10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fruitShopService.processTransactions(transactions));
    }
}
