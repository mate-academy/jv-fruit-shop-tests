package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.FruitShopUpdateService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopUpdateServiceImplTest {
    private static final Map<FruitTransaction.OperationType, OperationHandler> MAP = Map.of(
            FruitTransaction.OperationType.BALANCE, new BalanceHandler());
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
                new FruitTransaction(FruitTransaction.OperationType.BALANCE, "banana", 5));
        fruitShopUpdateService.update(expectedFruitShopUpdate, MAP);
        assertEquals(5, (int) Storage.storage.get("banana"));
    }
}
