package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_balance_ok() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertTrue(actual instanceof BalanceOperation);
    }

    @Test
    void getHandler_return_ok() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertTrue(actual instanceof ReturnOperation);
    }

    @Test
    void getHandler_purchase_ok() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertTrue(actual instanceof PurchaseOperation);
    }

    @Test
    void getHandler_supply_ok() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertTrue(actual instanceof SupplyOperation);
    }

}
