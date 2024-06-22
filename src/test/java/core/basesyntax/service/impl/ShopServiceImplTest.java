package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.config.AppConfig;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers =
                appConfig.getOperationHandlers();
        shopService = new ShopServiceImpl(operationHandlers);
        inventory = new HashMap<>();
    }

    @Test
    void processTransactions_validTransactions_success() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1)
        );
        shopService.processTransactions(transactions, inventory);
        assertEquals(13, inventory.get("apple"));
    }

    @Test
    void processTransactions_invalidOperation_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(null, "apple", 10)
        );
        assertThrows(RuntimeException.class, () ->
                shopService.processTransactions(transactions, inventory));
    }

    @Test
    void processTransactions_multipleFruits_success() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 2)
        );
        shopService.processTransactions(transactions, inventory);
        assertEquals(15, inventory.get("apple"));
        assertEquals(17, inventory.get("banana"));
    }

    @Test
    void processTransactions_negativeQuantity_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -10)
        );
        assertThrows(RuntimeException.class, () ->
                shopService.processTransactions(transactions, inventory));
    }

    @Test
    void processTransactions_largeNumberOfTransactions_success() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1000),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 500),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 300),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 200)
        );
        shopService.processTransactions(transactions, inventory);
        assertEquals(1100, inventory.get("apple"));
    }

    @Test
    void processTransactions_boundaryValues_success() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",
                        Integer.MAX_VALUE),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple",
                        Integer.MAX_VALUE)
        );
        shopService.processTransactions(transactions, inventory);
        assertEquals(0, inventory.get("apple"));
    }

    @Test
    void processTransactions_invalidInputData_throwsException() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", -5)
        );
        assertThrows(RuntimeException.class, () ->
                shopService.processTransactions(transactions, inventory));
    }

    // New test cases
    @Test
    void processTransactions_emptyTransactions_success() {
        List<FruitTransaction> transactions = List.of();
        shopService.processTransactions(transactions, inventory);
        assertEquals(0, inventory.size());
    }

    @Test
    void processTransactions_zeroQuantity_success() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 0),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 0),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 0)
        );
        shopService.processTransactions(transactions, inventory);
        assertEquals(0, inventory.get("apple"));
    }

    @Test
    void processTransactions_nullTransaction_throwsException() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                null
        );
        assertThrows(RuntimeException.class, () ->
                shopService.processTransactions(transactions, inventory));
    }

    @Test
    void processTransactions_nullInventory_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10)
        );
        assertThrows(RuntimeException.class, () ->
                shopService.processTransactions(transactions, null));
    }
}
