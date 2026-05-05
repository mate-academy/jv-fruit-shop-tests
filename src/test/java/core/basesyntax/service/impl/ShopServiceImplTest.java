package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        map.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        strategy = new OperationStrategyImpl(map);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void process_balanceOperation_setsInitialBalance() {
        ShopService service = new ShopServiceImpl(strategy);
        List<FruitTransaction> input = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));
        service.process(input);
        assertEquals(100, Storage.getStorage().get("banana"));
    }

    @Test
    void process_emptyList_shouldDoNothing() {
        ShopService service = new ShopServiceImpl(strategy);
        service.process(List.of());
        assertTrue(Storage.getStorage().isEmpty());
    }
}
