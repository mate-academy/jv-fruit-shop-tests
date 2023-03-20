package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.CalculateStrategy;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculateStrategyImplTest {
    private static CalculateStrategy calculateStrategy;
    private static final String DEFAULT_FRUIT = "apple";
    private static final int DEFAULT_QUANTITY = 50;

    @BeforeClass
    public static void beforeClass() throws Exception {
        calculateStrategy = new CalculateStrategyImpl();
    }

    @Test
    public void getHandler_getBalanceHandler_Ok() {
        FruitTransaction fruitTransaction =
                    new FruitTransaction(FruitTransaction.Operation.BALANCE,
                            DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler expected = new BalanceHandler();
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_getSupplyHandler_Ok() {
        FruitTransaction fruitTransaction =
                    new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler expected = new SupplyHandler();
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_getReturnHandler_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler expected = new ReturnHandler();
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_getPurchaseHandler_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler expected = new PurchaseHandler();
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
