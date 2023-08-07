package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.FruitShopUpdateService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopUpdateServiceImplTest {
    private static final Map<FruitTransaction.OperationType, OperationHandler> MAP = Map.of(
            FruitTransaction.OperationType.BALANCE, new BalanceHandler(),
            FruitTransaction.OperationType.RETURN, new ReturnHandler(),
            FruitTransaction.OperationType.PURCHASE, new PurchaseHandler(),
            FruitTransaction.OperationType.SUPPLY, new SupplyHandler());
    private FruitShopUpdateService fruitShopUpdateService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl() {
            @Override
            public OperationHandler get(FruitTransaction.OperationType operation,
                                        Map<FruitTransaction.OperationType, OperationHandler> map) {
                return map.get(operation);
            }
        };
        fruitShopUpdateService = new FruitShopUpdateServiceImpl(operationStrategy);
    }

    @Test
    void update_validTransaction_ok() {
        Storage.storage.put("banana", 20);
        List<FruitTransaction> expectedFruitShopUpdate = List.of(
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.OperationType.RETURN, "banana", 10),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY, "banana", 10));
        fruitShopUpdateService.update(expectedFruitShopUpdate, MAP);
        assertEquals(35, (int) Storage.storage.get("banana"));
    }
}
