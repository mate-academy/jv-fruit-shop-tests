package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.OperationType;
import core.basesyntax.strategy.OperationStrategyProvider;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceTest {
    private FruitShopService service;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
        InventoryService inventoryService = new InventoryService();
        OperationStrategyProvider strategyProvider = new OperationStrategyProvider(
                Map.of(
                        OperationType.ADD, new PurchaseOperationHandler(),
                        OperationType.SUPPLY, new SupplyOperationHandler(),
                        OperationType.RETURN, new ReturnOperationHandler()
                )
        );
        service = new FruitShopService(inventoryService, strategyProvider);
    }

    @Test
    void processTransactions_SuccessfulOperations() {
        Storage.inventory.put("apple", 10);
        Storage.inventory.put("banana", 5);

        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(OperationType.ADD, "apple", 4),
                new FruitTransaction(OperationType.SUPPLY, "banana", 3),
                new FruitTransaction(OperationType.RETURN, "apple", 2)
        );

        service.processTransactions(transactions);

        assertEquals(8, Storage.inventory.get("banana"));
    }

    @Test
    void processTransactions_FruitNotFound_ThrowsIllegalArgumentException() {
        Storage.inventory.put("apple", 10);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(OperationType.SUPPLY, "orange", 1)
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.processTransactions(transactions)
        );
        assertEquals("Fruit not found: orange", exception.getMessage());
    }

    @Test
    void processTransactions_NegativeQuantity_ThrowsIllegalArgumentException() {
        Storage.inventory.put("apple", 10);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(OperationType.ADD, "apple", -1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.processTransactions(transactions)
        );
    }

    @Test
    void processTransactions_EmptyList_NoChangeInInventory() {
        Storage.inventory.put("apple", 10);

        service.processTransactions(List.of());

        assertEquals(10, Storage.inventory.get("apple"));
    }
}
