package service.impl;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;
import strategy.OperationStrategy;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Transaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Transaction.Operation.BALANCE, new BalanceOperation());
        handlerMap.put(Transaction.Operation.SUPPLY, new SupplyOperation());
        handlerMap.put(Transaction.Operation.PURCHASE, new PurchaseOperation());
        handlerMap.put(Transaction.Operation.RETURN, new ReturnOperation());

        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void getBalanceOperation_ok() {
        OperationHandler handler = operationStrategy.get(Transaction.Operation.BALANCE);

        assertNotNull(handler, "Must have got the "
                + Transaction.Operation.BALANCE
                + " operation but got null");
        assertInstanceOf(BalanceOperation.class, handler,
                "Expected handler of the BalanceOperation type");
    }

    @Test
    void getSupplyOperation_ok() {
        OperationHandler handler = operationStrategy.get(Transaction.Operation.SUPPLY);

        assertNotNull(handler, "Must have got the "
                + Transaction.Operation.SUPPLY
                + " operation but got null");
        assertInstanceOf(SupplyOperation.class, handler,
                "Expected handler of the SupplyOperation type");
    }

    @Test
    void getPurchaseOperation_ok() {
        OperationHandler handler = operationStrategy.get(Transaction.Operation.PURCHASE);

        assertNotNull(handler, "Must have got the "
                + Transaction.Operation.PURCHASE
                + " operation but got null");
        assertInstanceOf(PurchaseOperation.class, handler,
                "Expected handler of the PurchaseOperation type");
    }

    @Test
    void getReturnOperation_ok() {
        OperationHandler handler = operationStrategy.get(Transaction.Operation.RETURN);

        assertNotNull(handler, "Must have got the "
                + Transaction.Operation.RETURN
                + " operation but got null");
        assertInstanceOf(ReturnOperation.class, handler,
                "Expected handler of the ReturnOperation type");
    }
}
