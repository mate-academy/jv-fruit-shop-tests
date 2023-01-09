package core.basesyntax.service.operations;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationStrategyTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> strategies;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        strategies = new HashMap<>();
    }

    @Before
    public void setUp() {
        strategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        strategies.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        strategies.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        strategies.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        strategy = new OperationStrategyImpl(strategies);
    }

    @Test
    public void with_Key_Ok() {
        OperationHandler expected = strategies.get(FruitTransaction.Operation.BALANCE);
        OperationHandler actual = strategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_null_NotOk() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Type cannot be null");
        strategy.get(null);
    }
}
