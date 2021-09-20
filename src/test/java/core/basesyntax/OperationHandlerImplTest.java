package core.basesyntax;

import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.Operations;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import core.basesyntax.service.operationstrategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerImplTest {
    private static OperationStrategyImpl operationStrategy;

    @BeforeClass
    public static void beforeAll() {
        OperationHandler supplyOperationHandler = new SupplyOperationHandler();
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        Map<String, OperationHandler> operationHandlerMap = new HashMap();
        operationHandlerMap.put(String.valueOf(Operations.s), supplyOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.b), balanceOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.r), returnOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.p), purchaseOperationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void operationSupply_OK() {
        OperationHandler actual = operationStrategy.getOperationHandler("s");
        OperationHandler expected = new SupplyOperationHandler();
        Assert.assertEquals("OperationStrategy works incorrect "
                        + "with SupplyOperationHandler", expected.getClass(), actual.getClass());
    }

    @Test
    public void operationBalance_OK() {
        OperationHandler actual = operationStrategy.getOperationHandler("b");
        OperationHandler expected = new BalanceOperationHandler();
        Assert.assertEquals("OperationStrategy works incorrect with SupplyOperationHandler",
                expected.getClass(), actual.getClass());
    }

    @Test
    public void operationPurchase_OK() {
        OperationHandler actual = operationStrategy.getOperationHandler("p");
        OperationHandler expected = new PurchaseOperationHandler();
        Assert.assertEquals("OperationStrategy works incorrect "
                        + "with PurchaseOperationHandler", expected.getClass(), actual.getClass());
    }

    @Test
    public void supplyOperation_OK() {
        OperationHandler actual = operationStrategy.getOperationHandler("s");
        OperationHandler expected = new SupplyOperationHandler();
        Assert.assertEquals("OperationStrategy works incorrect "
                        + "with SupplyOperationHandler", expected.getClass(), actual.getClass());
    }

    @Test
    public void incorrectOperation_NO_OK() {
        OperationHandler actual = operationStrategy.getOperationHandler("f");
        Assert.assertNull("OperationStrategy works "
                + "incorrect with Incorrect Data", actual);
    }
}
