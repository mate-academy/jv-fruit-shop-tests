package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.impl.AddOperationServiceImpl;
import core.basesyntax.strategy.impl.SubtractOperationServiceImpl;
import java.util.Map;
import org.junit.Test;

public class OperationServiceStrategyTest {
    private Map<String,OperationService> operationServiceMap;

    @Test
    public void operationServiceStrategy_testBalanceOperation_Ok() {
        Class actual = new OperationServiceStrategy()
                .getOperationServiceByOperationType("b")
                .getClass();
        Class expected = AddOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationServiceStrategy_testPurchaseOperation_Ok() {
        Class actual = new OperationServiceStrategy()
                .getOperationServiceByOperationType("p")
                .getClass();
        Class expected = SubtractOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationServiceStrategy_testReturnOperation_Ok() {
        Class actual = new OperationServiceStrategy()
                .getOperationServiceByOperationType("r")
                .getClass();
        Class expected = AddOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationServiceStrategy_testSupplyOperation_Ok() {
        Class actual = new OperationServiceStrategy()
                .getOperationServiceByOperationType("s")
                .getClass();
        Class expected = AddOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operationServiceStrategy_testNullOperation_NotOk() {
        Class nullOperation = new OperationServiceStrategy()
                .getOperationServiceByOperationType(null)
                .getClass();
    }

    @Test(expected = RuntimeException.class)
    public void operationServiceStrategy_testInvalidOperation_NotOk() {
        Class invalidOperation = new OperationServiceStrategy()
                .getOperationServiceByOperationType("k")
                .getClass();
    }
}
