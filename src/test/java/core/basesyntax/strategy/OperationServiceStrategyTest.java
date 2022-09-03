package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.impl.AddOperationServiceImpl;
import core.basesyntax.strategy.impl.SubtractOperationServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceStrategyTest {
    private static OperationServiceStrategy operationService;

    @BeforeClass
    public static void beforeClass() {
        operationService = new OperationServiceStrategy();
    }

    @Test
    public void operationServiceStrategy_testBalanceOperation_Ok() {
        Class<? extends OperationService> actual = operationService
                .getOperationServiceByOperationType("b")
                .getClass();
        Class<AddOperationServiceImpl> expected = AddOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationServiceStrategy_testPurchaseOperation_Ok() {
        Class<? extends OperationService> actual = operationService
                .getOperationServiceByOperationType("p")
                .getClass();
        Class<SubtractOperationServiceImpl> expected = SubtractOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationServiceStrategy_testReturnOperation_Ok() {
        Class<? extends OperationService> actual = operationService
                .getOperationServiceByOperationType("r")
                .getClass();
        Class<AddOperationServiceImpl> expected = AddOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationServiceStrategy_testSupplyOperation_Ok() {
        Class<? extends OperationService> actual = operationService
                .getOperationServiceByOperationType("s")
                .getClass();
        Class<AddOperationServiceImpl> expected = AddOperationServiceImpl.class;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operationServiceStrategy_testNullOperation_NotOk() {
        Class<? extends OperationService> nullOperation = operationService
                .getOperationServiceByOperationType(null)
                .getClass();
    }

    @Test(expected = RuntimeException.class)
    public void operationServiceStrategy_testInvalidOperation_NotOk() {
        Class<? extends OperationService> invalidOperation = operationService
                .getOperationServiceByOperationType("k")
                .getClass();
    }
}
