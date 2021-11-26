package core.basesyntax.operationstrategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationStrategyTest {
    private String operation;
    private OperationStrategy operationStrategy = new OperationStrategy();

    @Test
    public void get_operation_ok() {
        OperationHandler actual;
        OperationHandler expected;
        operation = "b";
        expected = new AddOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());

        operation = "s";
        expected = new AddOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());

        operation = "p";
        expected = new ReduceOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());

        operation = "r";
        expected = new AddOperationHandler();
        actual = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
