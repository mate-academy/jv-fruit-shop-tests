package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getOperationHandler_getBalanceOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertTrue(handler instanceof BalanceOperation);
    }

    @Test
    void getOperationHandler_getPurchaseOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertTrue(handler instanceof PurchaseOperation);
    }

    @Test
    void getOperationHandler_getReturnOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertTrue(handler instanceof ReturnOperation);
    }

    @Test
    void getOperationHandler_getSupplyOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertTrue(handler instanceof SupplyOperation);
    }

    @Test
    void getOperationHandler_getHandlerFromNullOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.get(null));
    }
}
