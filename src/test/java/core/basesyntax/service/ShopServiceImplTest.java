package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void shopService_validTransaction_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, "banana", 0),
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, "banana", 10),
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN, "banana", 5)
        );
        shopService.process(transactions);
        Integer banana = Storage.fruits.getOrDefault("banana", 0);
        assertEquals(10, banana);
    }

    @Test
    void process_emptyTransactions_ok() {
        shopService.process(List.of());
        assertEquals(0, Storage.fruits.size());
    }

    @Test
    void shopService_invalidTransaction_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, "banana", -50),
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, "banana", 20)
        );
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(transactions),
                "Processing transactions with invalid quantities"
                        + " should throw an IllegalArgumentException.");
    }
}
