package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private ShopServiceImpl shopService;

    @AfterEach
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
    void getStorage_returnsCorrectData_ok() {
        Storage.storage.put("apple", 10);
        Map<String, Integer> storage = Storage.storage;
        assertEquals(1, storage.size());
        assertEquals(10, storage.get("apple"));
    }

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }
}
