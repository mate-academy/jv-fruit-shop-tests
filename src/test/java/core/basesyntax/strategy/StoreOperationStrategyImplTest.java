package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.StoreOperation;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StoreOperationStrategyImplTest {
    private static StoreOperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<StoreOperation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(StoreOperation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(StoreOperation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(StoreOperation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(StoreOperation.RETURN, new ReturnOperationHandler());
        operationStrategy = new StoreOperationStrategyImpl(handlerMap);
    }

    @Test
    public void getBalanceOperation_Ok() {
        OperationHandler operation = operationStrategy.getOperation(StoreOperation.BALANCE);
        assertEquals(BalanceOperationHandler.class, operation.getClass());
    }

    @Test
    public void getSupplyOperation_Ok() {
        OperationHandler operation = operationStrategy.getOperation(StoreOperation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, operation.getClass());
    }

    @Test
    public void getPurchaseOperation_Ok() {
        OperationHandler operation = operationStrategy.getOperation(StoreOperation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, operation.getClass());
    }

    @Test
    public void getReturnOperation_Ok() {
        OperationHandler operation = operationStrategy.getOperation(StoreOperation.RETURN);
        assertEquals(ReturnOperationHandler.class, operation.getClass());
    }
}
