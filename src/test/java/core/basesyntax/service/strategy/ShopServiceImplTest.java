package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_multipleTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20)
        );
        shopService.process(transactions);
        assertEquals(80, Storage.getFruitStorage().get("apple"));
        assertEquals(50, Storage.getFruitStorage().get("banana"));
    }

    @Test
    void process_emptyList_ok() {
        shopService.process(List.of());
        assertTrue(Storage.getFruitStorage().isEmpty());
    }

    @Test
    void process_singleTransaction_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 75)
        );
        shopService.process(transactions);
        assertEquals(75, Storage.getFruitStorage().get("orange"));
        assertEquals(1, Storage.getFruitStorage().size());
    }

    @Test
    void process_allOperationTypes_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 40),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );
        shopService.process(transactions);
        assertEquals(100, Storage.getFruitStorage().get("apple"));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
