package core.basesyntax;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.amount.AdditionHandler;
import core.basesyntax.service.amount.AmountHandler;
import core.basesyntax.service.amount.SubtractionHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyTest {
    private static final FruitRecord.Type BALANCE = FruitRecord.Type.BALANCE;
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;
    private static final FruitRecord.Type RETURN = FruitRecord.Type.RETURN;
    private static final FruitRecord.Type PURCHASE = FruitRecord.Type.PURCHASE;
    private static Map<FruitRecord.Type, AmountHandler> strategies;
    private static OperationStrategy operationStrategy;
    private static AmountHandler additionHandler;
    private static AmountHandler subtractionHandler;

    @Before
    public void setUp() {
        additionHandler = new AdditionHandler();
        subtractionHandler = new SubtractionHandler();
        strategies = new HashMap<>();
        strategies.put(BALANCE, additionHandler);
        strategies.put(SUPPLY, additionHandler);
        strategies.put(RETURN, additionHandler);
        strategies.put(PURCHASE, subtractionHandler);
        operationStrategy = new OperationStrategyImpl(strategies);
    }

    @Test
    public void getAdditionHandlerStrategyByBalance_get_OK() {
        String expected = strategies.get(BALANCE).getClass().getSimpleName();
        String actual = operationStrategy.get(BALANCE).getClass().getSimpleName();
        Assert.assertEquals("Test failed! For operation type "
                + BALANCE.name() + " you should get "
                + expected + " handler.", expected, actual);
    }

    @Test
    public void getAdditionHandlerStrategyBySupply_get_OK() {
        String expected = strategies.get(SUPPLY).getClass().getSimpleName();
        String actual = operationStrategy.get(SUPPLY).getClass().getSimpleName();
        Assert.assertEquals("Test failed! For operation type "
                + SUPPLY.name() + " you should get "
                + expected + " handler.", expected, actual);
    }

    @Test
    public void getAdditionHandlerStrategyByReturn_get_OK() {
        String expected = strategies.get(RETURN).getClass().getSimpleName();
        String actual = operationStrategy.get(RETURN).getClass().getSimpleName();
        Assert.assertEquals("Test failed! For operation type "
                + RETURN.name() + " you should get "
                + expected + " handler.", expected, actual);
    }

    @Test
    public void getSubtractionHandlerStrategyByPurchase_get_OK() {
        String expected = strategies.get(PURCHASE).getClass().getSimpleName();
        String actual = operationStrategy.get(PURCHASE).getClass().getSimpleName();
        Assert.assertEquals("Test failed! For operation type "
                + PURCHASE.name() + " you should get "
                + expected + " handler.", expected, actual);
    }
}
