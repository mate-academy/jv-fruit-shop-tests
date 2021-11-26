package core.basesyntax.service;

import core.basesyntax.model.ParseLine;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.OperationService;
import service.operationimpl.CreateOperation;
import service.operationimpl.MinusOperation;
import service.operationimpl.PlusOperation;
import strategy.OperationStrategy;
import strategy.implement.OperationStrategyImpl;

public class OperationStrategyImplText {
    private static OperationStrategy strategy;
    private ParseLine parseLine;
    private OperationService expexted;
    private OperationService actual;

    @BeforeClass
    public static void init() {
        strategy = new OperationStrategyImpl();
    }

    @Test
    public void strategy_get_b_ok() {
        parseLine = new ParseLine("b", "banana", 20);
        expexted = new CreateOperation();
        actual = strategy.getOperationService(parseLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void strategy_get_p_ok() {
        parseLine = new ParseLine("p", "banana", 20);
        expexted = new MinusOperation();
        actual = strategy.getOperationService(parseLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void strategy_get_s_ok() {
        parseLine = new ParseLine("s", "banana", 20);
        expexted = new PlusOperation();
        actual = strategy.getOperationService(parseLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void strategy_get_r_ok() {
        parseLine = new ParseLine("r", "banana", 20);
        expexted = new PlusOperation();
        actual = strategy.getOperationService(parseLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }
}
