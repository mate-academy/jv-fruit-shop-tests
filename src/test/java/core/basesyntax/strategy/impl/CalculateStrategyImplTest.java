package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.CalculateStrategy;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CalculateStrategyImplTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final int DEFAULT_QUANTITY = 50;
    private static CalculateStrategy calculateStrategy;
    private static FruitTransaction fruitTransaction;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        calculateStrategy = new CalculateStrategyImpl();
    }

    @Test
    public void getHandler_getBalanceHandler_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(BalanceHandler.class, actual.getClass());
    }

    @Test
    public void getHandler_getSupplyHandler_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(SupplyHandler.class, actual.getClass());
    }

    @Test
    public void getHandler_getReturnHandler_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(ReturnHandler.class, actual.getClass());
    }

    @Test
    public void getHandler_getPurchaseHandler_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                DEFAULT_FRUIT, DEFAULT_QUANTITY);
        OperationHandler actual = calculateStrategy.getHandler(fruitTransaction);
        assertEquals(PurchaseHandler.class, actual.getClass());
    }
}
