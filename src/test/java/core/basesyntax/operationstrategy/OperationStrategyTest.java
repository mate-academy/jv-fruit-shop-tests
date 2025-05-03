package core.basesyntax.operationstrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class OperationStrategyTest {
    private String operation;
    private OperationStrategy operationStrategy = new OperationStrategy();
    private OperationHandler actual;
    private OperationHandler expected;

    @Test
    public void get_operation_notOk() {
        operation = "q";
        actual = operationStrategy.get(operation);
        assertNull(actual);
    }

    @Test
    public void get_operationBalance_ok() {
        operation = "b";
        expected = new AddOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_operationSupply_ok() {
        operation = "s";
        expected = new AddOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_operationPurchase_ok() {
        operation = "p";
        expected = new ReduceOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_operationReturn_ok() {
        operation = "r";
        expected = new AddOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
