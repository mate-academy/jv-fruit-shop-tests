package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private FakeOperationStrategy fakeOperationStrategy;

    @BeforeEach
    void setUp() {
        fakeOperationStrategy = new FakeOperationStrategy();
        shopService = new ShopServiceImpl(fakeOperationStrategy);
        Storage.fruits.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30)
        );

        shopService.process(transactions);

        assertEquals(50, Storage.fruits.get("banana"));
        assertEquals(30, Storage.fruits.get("apple"));
    }

    @Test
    void process_emptyTransactions_ok() {
        shopService.process(List.of());
        assertEquals(0, Storage.fruits.size());
    }

    @Test
    void process_unknownOperation_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "mango", 10)
        );

        IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                        () -> shopService.process(transactions));
        assertEquals("Unknown operation: RETURN", exception.getMessage());
    }

    private static class FakeOperationStrategy implements OperationStrategy {
        private final Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();

        public FakeOperationStrategy() {
            handlers.put(FruitTransaction.Operation.BALANCE, (fruit, quantity) ->
                    Storage.fruits.put(fruit, quantity));
            handlers.put(FruitTransaction.Operation.PURCHASE, (fruit, quantity) ->
                    Storage.fruits.put(fruit, Storage.fruits.getOrDefault(fruit, 0) - quantity));
            handlers.put(FruitTransaction.Operation.SUPPLY, (fruit, quantity) ->
                    Storage.fruits.put(fruit, Storage.fruits.getOrDefault(fruit, 0) + quantity));
        }

        @Override
        public OperationHandler getHandler(FruitTransaction.Operation operation) {
            if (!handlers.containsKey(operation)) {
                throw new IllegalArgumentException("Unknown operation: " + operation);
            }
            return handlers.get(operation);
        }
    }
}
