package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation()
        ));
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void process_validTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10)
        );
        shopService.process(transactions);
        assertEquals(10, Storage.get("banana"));
    }

    @Test
    void process_transactionsNull_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(null);
        });
    }

    @Test
    void process_validSupplyAndPurchaseTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 15),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5)
        );
        shopService.process(transactions);
        assertEquals(30, Storage.get("apple"));
    }

    @Test
    void process_emptyTransactions_Ok() {
        List<FruitTransaction> transactions = List.of();
        shopService.process(transactions);
        assertEquals(0, Storage.get("banana"));
    }
}
