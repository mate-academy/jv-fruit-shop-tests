package core.basesyntax.strategy;

import core.basesyntax.exception.OperationException;
import core.basesyntax.service.operation.AddOperationHandlerImpl;
import core.basesyntax.service.operation.PurchaseOperationHandlerImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static final String UNKNOWN_OPERATION_EXAMPLE = "1234567890";

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test (expected = OperationException.class)
    public void get_unknownOperation_notOk() {
        operationStrategy.get(UNKNOWN_OPERATION_EXAMPLE);
    }

    @Test (expected = OperationException.class)
    public void get_emptyOperation_notOk() {
        operationStrategy.get("");
    }

    @Test (expected = OperationException.class)
    public void get_nullOperation_notOk() {
        operationStrategy.get(null);
    }

    @Test
    public void get_rOperation_ok() {
        Assert.assertEquals(AddOperationHandlerImpl.class, operationStrategy.get("r").getClass());
    }

    @Test
    public void get_bOperation_ok() {
        Assert.assertEquals(AddOperationHandlerImpl.class, operationStrategy.get("b").getClass());
    }

    @Test
    public void get_sOperation_ok() {
        Assert.assertEquals(AddOperationHandlerImpl.class, operationStrategy.get("s").getClass());
    }

    @Test
    public void get_pOperation_ok() {
        Assert.assertEquals(PurchaseOperationHandlerImpl.class,
                operationStrategy.get("p").getClass());
    }
}
