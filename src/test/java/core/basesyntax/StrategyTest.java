package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.Operations;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import core.basesyntax.service.operationstrategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StrategyTest {
    private static OperationStrategyImpl operationStrategy;

    @BeforeAll
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
    void operationSupply() {
        OperationHandler actual = operationStrategy.getOperationHandler("s");
        OperationHandler expected = new SupplyOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with SupplyOperationHandler");
    }

    @Test
    void operationBalance() {
        OperationHandler actual = operationStrategy.getOperationHandler("b");
        OperationHandler expected = new BalanceOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with SupplyOperationHandler");
    }

    @Test
    void operationPurchase() {
        OperationHandler actual = operationStrategy.getOperationHandler("p");
        OperationHandler expected = new PurchaseOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with PurchaseOperationHandler");
    }

    @Test
    void supplyOperation() {
        OperationHandler actual = operationStrategy.getOperationHandler("s");
        OperationHandler expected = new SupplyOperationHandler();
        assertEquals(expected.getClass(), actual.getClass(), "OperationStrategy works incorrect "
                + "with SupplyOperationHandler");
    }

    @Test
    void incorrectOperation() {
        OperationHandler actual = operationStrategy.getOperationHandler("f");
        assertEquals(null, actual, "OperationStrategy works incorrect with Incorrect Data");
    }
}
