package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.StrategyStorageImpl;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StrategyStorageImplTest {
    private static final String APPLE = "apple";
    private static final int QUANTITY = 100;

    private StrategyStorage strategyStorage;
    private Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());

        strategyStorage = new StrategyStorageImpl();
        ((StrategyStorageImpl) strategyStorage).setHandlers(handlers);
    }

    @Test
    void getStrategy_validOperation_returnsCorrectHandler() {
        FruitTransaction validTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY);
        OperationHandler expectedHandler = handlers.get(FruitTransaction.Operation.BALANCE);

        OperationHandler actualHandler = strategyStorage.getStrategy(validTransaction);

        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void getStrategy_nullOperation_throwsRuntimeException() {
        FruitTransaction invalidTransaction = new FruitTransaction(null, APPLE, QUANTITY);

        assertThrows(RuntimeException.class, () -> strategyStorage.getStrategy(invalidTransaction));
    }
}