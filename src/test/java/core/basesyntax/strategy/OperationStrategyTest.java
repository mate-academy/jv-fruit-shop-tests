package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<Transaction.Operation, OperationHandler> map;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        map = new HashMap<>();
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void operationStrategyPurchaseIsValid_Ok() {
        operationHandler = new PurchaseOperationHandler();
        map.put(Transaction.Operation.PURCHASE, operationHandler);
        OperationHandler expected = operationHandler;
        assertEquals(expected, operationStrategy.getByOperation(Transaction.Operation.PURCHASE));
    }

    @Test
    public void operationStrategyReturnIsValid_Ok() {
        operationHandler = new ReturnOperationHandler();
        map.put(Transaction.Operation.RETURN, operationHandler);
        OperationHandler expected = operationHandler;
        assertEquals(expected, operationStrategy.getByOperation(Transaction.Operation.RETURN));
    }

    @Test
    public void operationStrategySupplyIsValid_Ok() {
        operationHandler = new SupplyOperationHandler();
        map.put(Transaction.Operation.SUPPLY, operationHandler);
        OperationHandler expected = operationHandler;
        assertEquals(expected, operationStrategy.getByOperation(Transaction.Operation.SUPPLY));
    }

    @Test
    public void operationStrategyBalanceIsValid_Ok() {
        operationHandler = new BalanceOperationHandler();
        map.put(Transaction.Operation.BALANCE, operationHandler);
        OperationHandler expected = operationHandler;
        assertEquals(expected, operationStrategy.getByOperation(Transaction.Operation.BALANCE));
    }
}
