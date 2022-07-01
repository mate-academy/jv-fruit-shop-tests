package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategyImpl operationStrategyImpl = new OperationStrategyImpl();

    @BeforeClass
    public static void beforeClass() {
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.RETURN,
                new ReturnOperationHandler());
    }

    @Test
    public void get_balanceOperation_Ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl
                .get(Transaction.Operation.BALANCE);
        Class<BalanceOperationHandler> actual = (Class<BalanceOperationHandler>)
                                                    operationHandler.getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperation_Ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl
                .get(Transaction.Operation.PURCHASE);
        Class<PurchaseOperationHandler> actual = (Class<PurchaseOperationHandler>)
                                                    operationHandler.getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnOperation_Ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl.get(Transaction.Operation.RETURN);
        Class<ReturnOperationHandler> actual = (Class<ReturnOperationHandler>)
                                                    operationHandler.getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyOperation_Ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl.get(Transaction.Operation.SUPPLY);
        Class<SupplyOperationHandler> actual = (Class<SupplyOperationHandler>)
                                                    operationHandler.getClass();
        assertEquals(expected, actual);
    }
}
