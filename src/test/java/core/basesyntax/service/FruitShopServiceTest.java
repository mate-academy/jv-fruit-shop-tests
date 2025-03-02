package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyProvider;
import java.util.HashMap;
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
            FruitTransaction.OperationType operation = FruitTransaction.OperationType.BALANCE;
            for (FruitTransaction.OperationType type : FruitTransaction.OperationType.values()) {
                if (inventory.containsKey(fruit)) {
                    operation = type;
                    break;
                }
            }

            switch (operation) {
                case BALANCE, SUPPLY, RETURN -> inventoryService.addFruit(fruit, quantity);
                case PURCHASE -> inventoryService.removeFruit(fruit, quantity);
                default -> throw new IllegalArgumentException("Unsupported operation: "
                        + operation);
            }
        }
    }

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(new HashMap<>());
        strategyProvider = new OperationStrategyProvider() {
            @Override
            public OperationHandler getHandler(FruitTransaction.OperationType operation) {
                return new TestOperationHandler(inventoryService);
            }
        };
        fruitShopService = new FruitShopService(inventoryService, strategyProvider);
    }

    @Test
    void processTransactions_validTransactions_success() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.OperationType.BALANCE, "apple", 30),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY, "banana", 20),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE, "apple", 10),
                new FruitTransaction(FruitTransaction.OperationType.RETURN, "banana", 5)
        );

        inventoryService.addFruit("apple", 0);
        inventoryService.addFruit("banana", 0);

        Assertions.assertDoesNotThrow(() -> fruitShopService.processTransactions(transactions));
        Assertions.assertEquals(40, inventoryService.getQuantity("apple"));
        Assertions.assertEquals(25, inventoryService.getQuantity("banana"));
    }
}
