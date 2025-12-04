package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Storage storage;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        storage = new Storage();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(storage, operationStrategy);
    }

    @Test
    void process_nullList_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(null));
    }

    @Test
    void process_transactionIsNull_notOk() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(list));
    }

    @Test
    void process_transactionWithNullOperation_notOk() {
        FruitTransaction transaction = new FruitTransaction(null, "apple", 10);
        List<FruitTransaction> list = List.of(transaction);
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(list));
    }

    @Test
    void process_validSingleTransaction_isOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10
        );
        shopService.process(List.of(transaction));
        assertEquals(10, storage.getAll().get("apple"));
    }

    @Test
    void process_multipleTransactions_isOk() {
        List<FruitTransaction> list = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5)
        );
        shopService.process(list);
        assertEquals(5, storage.getAll().get("apple"));
    }

    @Test
    void process_operationHandlerMissing_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10
        );
        List<FruitTransaction> list = List.of(transaction);
        assertThrows(RuntimeException.class,
                () -> shopService.process(list));
    }
}
