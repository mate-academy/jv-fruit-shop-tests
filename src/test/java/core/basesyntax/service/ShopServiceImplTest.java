package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        OperationStrategy operationStrategy = new TestOperationStrategy();
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        List<FruitTransaction> transactions = List.of(transaction);

        shopService.process(transactions);

        assertEquals(100, Storage.storage.get("apple"));
    }

    @Test
    void process_nullTransactions_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> shopService.process(null));
    }

    @Test
    void process_listContainsNullTransaction_throwIllegalArgumentException() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                null
        );

        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions));
    }

    @Test
    void getStorage_returnsUnmodifiableMap_ok() {
        Map<String, Integer> storage = shopService.getStorage();
        assertThrows(UnsupportedOperationException.class, () -> storage.put("orange", 50));
    }

    @Test
    void getStorage_returnsCorrectData_ok() {
        Storage.storage.put("apple", 10);
        Map<String, Integer> storage = shopService.getStorage();
        assertEquals(1, storage.size());
        assertEquals(10, storage.get("apple"));
    }

    private static class TestOperationStrategy implements OperationStrategy {
        @Override
        public OperationHandler get(FruitTransaction.Operation operation) {
            if (operation == FruitTransaction.Operation.BALANCE) {
                return new TestBalanceHandler();
            }
            throw new IllegalArgumentException("Unsupported operation: " + operation);
        }
    }

    private static class TestBalanceHandler implements OperationHandler {
        @Override
        public void handle(FruitTransaction transaction, Map<String, Integer> storage) {
            storage.put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
