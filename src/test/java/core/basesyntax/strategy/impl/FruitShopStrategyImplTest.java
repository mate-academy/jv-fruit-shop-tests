package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperationHandler;
import core.basesyntax.service.impl.PurchaseOperationHandler;
import core.basesyntax.service.impl.ReturnOperationHandler;
import core.basesyntax.service.impl.SupplyOperationHandler;
import core.basesyntax.strategy.FruitShopStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitShopStrategyImplTest {
    private static FruitShopStrategy fruitShopStrategy;

    @Before
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        fruitShopStrategy = new FruitShopStrategyImpl(operationHandlerMap);
    }

    @Test
    public void fruitShopStrategy_getOperationHandlerOfTheOperation_ok() {
        String actual = String.valueOf(fruitShopStrategy.get(Operation.BALANCE).getClass());
        String expected = String.valueOf(new BalanceOperationHandler().getClass());
        assertEquals(actual, expected);
    }
}
