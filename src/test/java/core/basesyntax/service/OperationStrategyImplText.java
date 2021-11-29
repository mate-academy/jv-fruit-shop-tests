package core.basesyntax.service;

import core.basesyntax.model.ParsedLine;
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
    private ParsedLine parsedLine;
    private OperationService expexted;
    private OperationService actual;

    @BeforeClass
    public static void init() {
        strategy = new OperationStrategyImpl();
    }

    @Test
    public void strategy_get_b_ok() {
        parsedLine = new ParsedLine("b", "banana", 20);
        expexted = new CreateOperation();
        actual = strategy.getOperationService(parsedLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void strategy_get_p_ok() {
        parsedLine = new ParsedLine("p", "banana", 20);
        expexted = new MinusOperation();
        actual = strategy.getOperationService(parsedLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void strategy_get_s_ok() {
        parsedLine = new ParsedLine("s", "banana", 20);
        expexted = new PlusOperation();
        actual = strategy.getOperationService(parsedLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void strategy_get_r_ok() {
        parsedLine = new ParsedLine("r", "banana", 20);
        expexted = new PlusOperation();
        actual = strategy.getOperationService(parsedLine);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }
}
