package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;
    private OperationHandler supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyOperation);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_supplyOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(supplyOperation, actual);
    }

    @Test
    void get_purchaseOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseOperation.class, actual.getClass());
    }

    @Test
    void get_returnOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(ReturnOperation.class, actual.getClass());
    }

    @Test
    void get_balanceOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperation.class, actual.getClass());
    }
}
