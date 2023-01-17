package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operationhandler.OperationHandler;
import core.basesyntax.strategy.operationhandler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.ReturnOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.SupplyOperationHandler;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy strategy;

    @BeforeClass
    public static void init() {
        operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Before
    public void fillStorage() {
        FruitStorage.fruitStorage.put("apple", 40);
    }

    @Test
    public void getHandler_balance_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> expected = BalanceOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void getHandler_purchase_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> expected = PurchaseOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void getHandler_return_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.RETURN).getClass();
        Class<?> expected = ReturnOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void getHandler_supply_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> expected = SupplyOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test (expected = NullPointerException.class)
    public void getHandler_nullOperation_isOk() {
        strategy.getHandler(null);
    }
}
