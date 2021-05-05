package core.basesyntax.countingoperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.operationswithfile.Transaction;
import org.junit.Test;

public class OperationsStrategyImplTest {
    private static final Transaction transaction = new Transaction();
    private static final OperationsStrategyImpl operationsStrategy = new OperationsStrategyImpl();

    @Test
    public void getStrategyWithReturnOperationType_ok() {
        transaction.setOperationType("r");
        transaction.setName("banana");
        transaction.setCount(50);
        Integer actual = operationsStrategy.getStrategy(transaction);
        Integer expected = 50;
        assertEquals(actual,expected);
    }

    @Test
    public void getStrategyWithPurchaseOperationType_ok() {
        transaction.setOperationType("p");
        transaction.setName("banana");
        transaction.setCount(50);
        Integer actual = operationsStrategy.getStrategy(transaction);
        Integer expected = -50;
        assertEquals(actual,expected);
    }

    @Test
    public void getStrategyWithSupplyOperationType_notOk() {
        transaction.setOperationType("s");
        transaction.setName("banana");
        transaction.setCount(50);
        Integer actual = operationsStrategy.getStrategy(transaction);
        Integer expected = -50;
        assertNotEquals(actual,expected);
    }
}
