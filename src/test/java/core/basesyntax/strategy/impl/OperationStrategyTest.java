package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void initializeService() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @AfterEach
    void cleanStorage() {
        Storage.cleanStorage();
    }

    @Test
    void getHandlerForTransaction_balanceTransaction_ok() {
        OperationHandler actual = operationStrategy.getHandlerForTransaction(
                FruitTransaction.Operation.BALANCE);
        assertInstanceOf(BalanceOperation.class, actual,
                "not returning proper handler for balance operation");
    }

    @Test
    void getHandlerForTransaction_purchaseTransaction_ok() {
        OperationHandler actual = operationStrategy.getHandlerForTransaction(
                FruitTransaction.Operation.PURCHASE);
        assertInstanceOf(PurchaseOperation.class, actual,
                "not returning proper handler for purchase operation");
    }

    @Test
    void getHandlerForTransaction_returnTransaction_ok() {
        OperationHandler actual = operationStrategy.getHandlerForTransaction(
                FruitTransaction.Operation.SUPPLY);
        assertInstanceOf(SupplyOperation.class, actual,
                "not returning proper handler for supply operation");
    }

    @Test
    void getHandlerForTransaction_supplyTransaction_ok() {
        OperationHandler actual = operationStrategy.getHandlerForTransaction(
                FruitTransaction.Operation.RETURN);
        assertInstanceOf(ReturnOperation.class, actual,
                "not returning proper handler for return operation");
    }

    @Test
    void getHandlerForTransaction_nullTransaction_ok() {
        OperationHandler actual = operationStrategy.getHandlerForTransaction(null);
        assertNull(actual, "not returning null for null operation");
    }
}
