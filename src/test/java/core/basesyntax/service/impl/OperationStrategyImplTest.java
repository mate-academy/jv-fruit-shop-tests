package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<Transaction.Operation, OperationHandler> operationHandlerMap = new
                HashMap<>();
        operationHandlerMap.put(Transaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Transaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Transaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Transaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperation_Balance_Ok() {
        OperationHandler operation = operationStrategy.get(Transaction.Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, operation.getClass());
    }

    @Test
    public void getOperation_Supply_Ok() {
        OperationHandler operation = operationStrategy.get(Transaction.Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, operation.getClass());
    }

    @Test
    public void getOperation_Purchase_Ok() {
        OperationHandler operation = operationStrategy.get(Transaction.Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, operation.getClass());
    }

    @Test
    public void getOperation_Return_Ok() {
        OperationHandler operation = operationStrategy.get(Transaction.Operation.RETURN);
        assertEquals(ReturnOperationHandler.class, operation.getClass());
    }
}
