package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation())));
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));

        shopService.process(transactions);

        assertEquals(20, Storage.getFruits().get("banana"));
        assertEquals(100, Storage.getFruits().get("apple"));
    }

    @Test
    void process_emptyList_ok() {
        shopService.process(List.of());

        assertTrue(Storage.getFruits().isEmpty());
    }

    @Test
    void process_unsupportedOperation_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20));

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }
}
