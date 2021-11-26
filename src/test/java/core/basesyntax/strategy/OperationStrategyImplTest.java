package core.basesyntax.strategy;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.exceptions.OperationException;
import core.basesyntax.services.operation.AddOperationHandlerImpl;
import core.basesyntax.services.operation.OperationHandler;
import core.basesyntax.services.operation.PurchaseOperationHandlerImpl;
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
    public void operation_strategy_inputNull_notOk() {
        operationStrategy.get(null);
    }

    @Test (expected = OperationException.class)
    public void operation_strategy_unknownOperation_notOk() {
        operationStrategy.get(UNKNOWN_OPERATION_EXAMPLE);
    }

    @Test (expected = OperationException.class)
    public void operation_strategy_emptyOperation_notOk() {
        operationStrategy.get("");
    }

    @Test (expected = OperationException.class)
    public void operation_strategy_nullOperation_notOk() {
        operationStrategy.get(null);
    }

    @Test
    public void operation_strategy_r_addValidOperation_ok() {
        OperationHandler expected = new AddOperationHandlerImpl(new FruitStorageDaoImpl());
        Assert.assertEquals(expected.getClass(), operationStrategy.get("r").getClass());
    }

    @Test
    public void operation_strategy_b_addValidOperation_ok() {
        OperationHandler expected = new AddOperationHandlerImpl(new FruitStorageDaoImpl());
        Assert.assertEquals(expected.getClass(), operationStrategy.get("b").getClass());
    }

    @Test
    public void operation_strategy_s_addValidOperation_ok() {
        OperationHandler expected = new AddOperationHandlerImpl(new FruitStorageDaoImpl());
        Assert.assertEquals(expected.getClass(), operationStrategy.get("s").getClass());
    }

    @Test
    public void operation_strategy_p_purchaseValidOperation_ok() {
        OperationHandler expected = new PurchaseOperationHandlerImpl(new FruitStorageDaoImpl());
        Assert.assertEquals(expected.getClass(), operationStrategy.get("p").getClass());
    }

}
