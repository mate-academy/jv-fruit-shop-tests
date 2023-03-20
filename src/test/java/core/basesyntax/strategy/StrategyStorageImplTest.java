package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.StrategyStorageImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyStorageImplTest {
    private static final String APPLE = "apple";
    private static final int QUANTITY = 100;
    private static StrategyStorageImpl strategyStorage;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        strategyStorage = new StrategyStorageImpl();
        strategyStorage.setHandlers(handlers);
    }

    @Test
    public void getStrategy_validBalanceOperation_ok() {
        FruitTransaction validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE,
                        QUANTITY);
        OperationHandler handler = strategyStorage.getStrategy(validTransaction);
        assertNotNull(handler);
        assertEquals(BalanceOperationHandler.class, handler.getClass());
    }

    @Test
    public void getStrategy_validPurchaseOperation_ok() {
        FruitTransaction validTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE,
                        QUANTITY);
        OperationHandler handler = strategyStorage.getStrategy(validTransaction);
        assertNotNull(handler);
        assertEquals(PurchaseOperationHandler.class, handler.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void getStrategy_nullOperation_noOk() {
        FruitTransaction invalidTransaction = new FruitTransaction(null, APPLE, QUANTITY);
        strategyStorage.getStrategy(invalidTransaction);
    }
}
