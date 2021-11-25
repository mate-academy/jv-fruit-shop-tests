package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.impl.AddOperationHandler;
import core.basesyntax.services.impl.PurchaseOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getOperationHandler_operationBuy_Ok() {
        assertEquals(operationStrategy.getOperationHandler("b").getClass(),
                AddOperationHandler.class);
    }

    @Test
    public void getOperationHandler_operationSupply_Ok() {
        assertEquals(operationStrategy.getOperationHandler("s").getClass(),
                AddOperationHandler.class);
    }

    @Test
    public void getOperationHandler_operationReturn_Ok() {
        assertEquals(operationStrategy.getOperationHandler("r").getClass(),
                AddOperationHandler.class);
    }

    @Test
    public void getOperationHandler_operationPurchase_Ok() {
        assertEquals(operationStrategy.getOperationHandler("p").getClass(),
                PurchaseOperationHandler.class);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandler_null_Ok() {
        operationStrategy.getOperationHandler(null);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandler_notValidOperation_Ok() {
        operationStrategy.getOperationHandler("z");
    }

}
