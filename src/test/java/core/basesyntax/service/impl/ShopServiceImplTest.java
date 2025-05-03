package core.basesyntax.service.impl;

import core.basesyntax.data.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void process_validTransactions_updatesStorageCorrectly() {
        // Створюємо список транзакцій для обробки
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 70)
        );

        shopService.process(transactions);

        Assertions.assertEquals(80, Storage.getInventory().get("apple"));
        Assertions.assertEquals(30, Storage.getInventory().get("banana"));
    }

    @Test
    void process_purchaseMoreThanStock_throwsException() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "grape", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "grape", 30)
        );

        Assertions.assertThrows(IllegalStateException.class,
                () -> shopService.process(transactions));
    }

    @Test
    void process_multipleSupplyAndReturnTransactions_accumulatesStock() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 30)
        );

        shopService.process(transactions);

        Assertions.assertEquals(100, Storage.getInventory().get("orange"));
    }

    @Test
    void process_noTransactions_storageRemainsUnchanged() {
        shopService.process(List.of());

        Assertions.assertTrue(Storage.getInventory().isEmpty());
    }
}
