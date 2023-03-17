package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.StrategyStorageImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StrategyStorageImplTest {
    private static final String APPLE = "apple";
    private static final int QUANTITY = 100;
    private StrategyStorage strategyStorage;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        strategyStorage = new StrategyStorageImpl();
        ((StrategyStorageImpl) strategyStorage).setHandlers(handlers);
    }

    @Test
    public void getStrategy_validOperation_ok() {
        FruitTransaction validTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY);
        OperationHandler handler = strategyStorage.getStrategy(validTransaction);
        assertNotNull(handler);
    }

    @Test
    public void getStrategy_validOperation_notOk() {
        FruitTransaction validTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY);
        OperationHandler handler = strategyStorage.getStrategy(validTransaction);
        assertNotNull(handler);
    }

    @Test
    public void getStrategy_nullOperation_noOk() {
        FruitTransaction invalidTransaction = new FruitTransaction(null, APPLE, QUANTITY);
        assertThrows(RuntimeException.class, () -> strategyStorage.getStrategy(invalidTransaction));
    }
}