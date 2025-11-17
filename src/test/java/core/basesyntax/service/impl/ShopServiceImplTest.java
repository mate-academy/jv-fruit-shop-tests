package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(Operation.BALANCE, new BalanceHandler());
        strategyMap.put(Operation.SUPPLY, new SupplyHandler());
        strategyMap.put(Operation.RETURN, new ReturnHandler());
        strategyMap.put(Operation.PURCHASE, new PurchaseHandler());

        OperationStrategy strategy = new OperationStrategy(strategyMap);

        shopService = new ShopServiceImpl(strategy);

        // Очистимо Storage
        Storage.put("apple", null);
    }

    @Test
    public void nullTransactionList() {
        NullPointerException exception =
                assertThrows(NullPointerException.class,
                        () -> shopService.process(null));

        assertEquals("Transaction list cannot be null", exception.getMessage());
    }
}
