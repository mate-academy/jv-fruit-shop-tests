package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_OperationBalance_Ok() {
        OperationHandler actual = operationStrategy.get(
                FruitTransaction.Operation.BALANCE);

        assertEquals(operationHandlers.get(FruitTransaction.Operation.BALANCE), actual);
    }

    @Test
    void get_OperationPurchase_Ok() {
        OperationHandler actual = operationStrategy.get(
                FruitTransaction.Operation.PURCHASE);

        assertEquals(operationHandlers.get(FruitTransaction.Operation.PURCHASE), actual);
    }

    @Test
    void get_OperationReturn_Ok() {
        OperationHandler actual = operationStrategy.get(
                FruitTransaction.Operation.RETURN);

        assertEquals(operationHandlers.get(FruitTransaction.Operation.RETURN), actual);
    }

    @Test
    void get_OperationSupply_Ok() {
        OperationHandler actual = operationStrategy.get(
                FruitTransaction.Operation.SUPPLY);

        assertEquals(operationHandlers.get(FruitTransaction.Operation.SUPPLY), actual);
    }
}
