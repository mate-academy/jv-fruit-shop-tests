package core.basesyntax.operationstrategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationStrategyTest {
    private String operation;
    private OperationStrategy operationStrategy = new OperationStrategy();

    @Test
    public void get_operation_ok() {
        OperationHandler expected;
        OperationHandler actual;
        operation = "b";
        actual = new AddOperationHandler();
        expected = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());

        operation = "s";
        actual = new AddOperationHandler();
        expected = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());

        operation = "p";
        actual = new ReduceOperationHandler();
        expected = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());

        operation = "r";
        actual = new AddOperationHandler();
        expected = operationStrategy.get(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
