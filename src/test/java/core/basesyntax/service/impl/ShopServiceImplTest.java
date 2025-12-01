package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.strategy.BalanceOperation;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.strategy.PurchaseOperation;
import core.basesyntax.service.strategy.ReturnOperation;
import core.basesyntax.service.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        handlers = new EnumMap<>(FruitTransaction.Operation.class);
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(107, result.get("banana"));
    }

    @Test
    void process_complexScenario_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(152, result.get("banana"));
        assertEquals(90, result.get("apple"));
    }

    @Test
    void process_emptyList_returnsEmptyMap() {
        List<FruitTransaction> transactions = new ArrayList<>();
        Map<String, Integer> result = shopService.process(transactions);
        assertTrue(result.isEmpty());
    }

    @Test
    void process_nullList_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(null));
    }

    @Test
    void process_nullTransaction_throwsException() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(transactions));
    }

    @Test
    void process_onlyBalance_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(100, result.get("apple"));
        assertEquals(50, result.get("banana"));
    }

    @Test
    void process_balanceOverwrite_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(50, result.get("apple"));
    }

    @Test
    void process_supplyOnly_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 25)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(175, result.get("apple"));
    }

    @Test
    void process_returnOnly_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(120, result.get("apple"));
    }

    @Test
    void process_purchaseReducesQuantity_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(70, result.get("apple"));
    }

    @Test
    void process_multipleFruits_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 75)
        );
        Map<String, Integer> result = shopService.process(transactions);
        assertEquals(3, result.size());
        assertEquals(100, result.get("apple"));
        assertEquals(50, result.get("banana"));
        assertEquals(75, result.get("orange"));
    }
}
