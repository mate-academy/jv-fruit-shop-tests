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
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopUpdateImplTest {
    private static final Map<Operation, OperationHandler> MAP = Map.of(
            Operation.BALANCE, new BalanceHandler(),
            Operation.RETURN, new ReturnHandler(),
            Operation.PURCHASE, new PurchaseHandler(),
            Operation.SUPPLY, new SupplyHandler());
    private ShopUpdateService shopUpdateService;
    private Strategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl() {
            @Override
            public OperationHandler get(Operation operation,
                                        Map<Operation, OperationHandler> map) {
                return map.get(operation);
            }
        };
        shopUpdateService = new ShopUpdateImpl(operationStrategy);
    }

    @Test
    void testUpdate_ok() {
        Storage.storage.put("banana", 20);
        List<FruitTransaction> expectedFruitShopUpdate = List.of(
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.RETURN, "banana", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 10));
        shopUpdateService.update(expectedFruitShopUpdate);
        assertEquals(35, (int) Storage.storage.get("banana"));
    }
}
