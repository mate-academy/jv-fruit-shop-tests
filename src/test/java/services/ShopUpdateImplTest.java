package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.handler.ReturnHandler;
import core.basesyntax.handler.SupplyHandler;
import core.basesyntax.impl.OperationStrategyImpl;
import core.basesyntax.impl.ShopUpdateImpl;
import core.basesyntax.service.ShopUpdateService;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopUpdateImplTest {
    private static final Map<Operation, OperationHandler> OPERATION_HANDLERS_MAP = new HashMap<>();

    static {
        OPERATION_HANDLERS_MAP.put(Operation.BALANCE, new BalanceHandler());
        OPERATION_HANDLERS_MAP.put(Operation.RETURN, new ReturnHandler());
        OPERATION_HANDLERS_MAP.put(Operation.PURCHASE, new PurchaseHandler());
        OPERATION_HANDLERS_MAP.put(Operation.SUPPLY, new SupplyHandler());
    }

    private Strategy operationStrategy;
    private ShopUpdateService shopUpdateService;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(OPERATION_HANDLERS_MAP);
        shopUpdateService = new ShopUpdateImpl(operationStrategy);
    }

    @AfterEach
    void clear() {
        Storage.storage.clear();
    }

    @Test
    void testUpdate_ok() {
        Storage.storage.put("banana", 20);
        List<FruitTransaction> expectedFruitShopUpdate = List.of(
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.RETURN, "banana", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 10));
        shopUpdateService.update(expectedFruitShopUpdate);
        Integer expected = 35;
        assertEquals(expected, Storage.storage.get("banana"));
    }
}
