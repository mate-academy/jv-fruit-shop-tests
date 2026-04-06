package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(storage));
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(storage));
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler(storage));
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(storage));
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20)
        );
        shopService.process(transactions);
        assertEquals(80, (int) storage.get("apple"));
    }

    @Test
    void process_nullTransactions_notOk() {
        assertThrows(RuntimeException.class, () -> shopService.process(null));
    }
}
