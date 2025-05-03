package service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;

public class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private OperationHandler balanceOperation;
    private OperationHandler purchaseOperation;
    private OperationHandler supplyOperation;
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
        supplyOperation = new SupplyOperation();
        returnOperation = new ReturnOperation();

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceOperation);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseOperation);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, supplyOperation);
        handlerMap.put(FruitTransaction.Operation.RETURN, returnOperation);

        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void getBalanceOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.BALANCE);

        assertNotNull(handler, "Handler should not be null for BALANCE operation");
        assertTrue(handler instanceof BalanceOperation,
                "Handler should be an instance of BalanceOperation");
    }

    @Test
    void getPurchaseOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);

        assertNotNull(handler, "Handler should not be null for PURCHASE operation");
        assertTrue(handler instanceof PurchaseOperation,
                "Handler should be an instance of PurchaseOperation");
    }

    @Test
    void getSupplyOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);

        assertNotNull(handler, "Handler should not be null for SUPPLY operation");
        assertTrue(handler instanceof SupplyOperation,
                "Handler should be an instance of SupplyOperation");
    }

    @Test
    void getReturnOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.RETURN);

        assertNotNull(handler, "Handler should not be null for RETURN operation");
        assertTrue(handler instanceof ReturnOperation,
                "Handler should be an instance of ReturnOperation");
    }
}
