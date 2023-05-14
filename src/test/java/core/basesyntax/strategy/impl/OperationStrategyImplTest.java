package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Activity;
import core.basesyntax.strategy.ActivityHandler;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private ActivityStrategyImpl operationStrategy;

    @Before
    public void setUp() {
        Map<Activity.Operation, ActivityHandler> activityHandlerMap =
                Map.of(Activity.Operation.BALANCE, new BalanceHandlerImpl(),
                        Activity.Operation.PURCHASE, new PurchaseHandlerImpl(),
                        Activity.Operation.RETURN, new ReturnHandlerImpl(),
                        Activity.Operation.SUPPLY, new SupplyHandlerImpl());
        operationStrategy = new ActivityStrategyImpl(activityHandlerMap);
    }

    @Test
    public void getHandlerBalanceOperation_Ok() {
        Class<? extends ActivityHandler> expectedClass = BalanceHandlerImpl.class;
        Activity.Operation testOperation = Activity.Operation.BALANCE;
        Class<? extends ActivityHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }

    @Test
    public void getHandlerReturnOperation_Ok() {
        Class<? extends ActivityHandler> expectedClass = ReturnHandlerImpl.class;
        Activity.Operation testOperation = Activity.Operation.RETURN;
        Class<? extends ActivityHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }

    @Test
    public void getHandlerPurchaseOperation_Ok() {
        Class<? extends ActivityHandler> expectedClass = PurchaseHandlerImpl.class;
        Activity.Operation testOperation = Activity.Operation.PURCHASE;
        Class<? extends ActivityHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }

    @Test
    public void getHandlerSupplyOperation_Ok() {
        Class<? extends ActivityHandler> expectedClass = SupplyHandlerImpl.class;
        Activity.Operation testOperation = Activity.Operation.SUPPLY;
        Class<? extends ActivityHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }
}
