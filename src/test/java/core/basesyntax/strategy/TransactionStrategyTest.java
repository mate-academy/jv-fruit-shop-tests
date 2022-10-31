package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.impl.BalanceHandler;
import core.basesyntax.service.handler.impl.PurchaseHandler;
import core.basesyntax.service.handler.impl.ReturnHandler;
import core.basesyntax.service.handler.impl.SupplyHandler;
import core.basesyntax.strategy.impl.TransactionStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyTest {
    private static TransactionStrategy transactionStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap;

    @BeforeClass
    public static void setUp() {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        transactionStrategy = new TransactionStrategyImpl(operationHandlersMap);
    }

    @Test
    public void get_BalanceHandler_Ok() {
        OperationHandler expected = new BalanceHandler();
        OperationHandler actual = transactionStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_PurchaseHandler_Ok() {
        OperationHandler expected = new PurchaseHandler();
        OperationHandler actual = transactionStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_ReturnHandler_Ok() {
        OperationHandler expected = new ReturnHandler();
        OperationHandler actual = transactionStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_SupplyHandler_Ok() {
        OperationHandler expected = new SupplyHandler();
        OperationHandler actual = transactionStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
