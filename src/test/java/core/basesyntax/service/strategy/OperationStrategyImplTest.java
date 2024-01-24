package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceHandler;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseHandler;
import core.basesyntax.operations.ReturnHandler;
import core.basesyntax.operations.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final FruitTransaction BALANCE_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
    private static final FruitTransaction PURCHASE_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
    private static final FruitTransaction SUPPLY_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 30);
    private static final FruitTransaction RETURN_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10);
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getBalanceOperation_isOk() {
        OperationHandler actualOperation =
                operationStrategy.get(BALANCE_FRUIT_TRANSACTION.getOperation());
        OperationHandler expectedOperation =
                operationHandlerMap.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expectedOperation, actualOperation);
    }

    @Test
    void getPurchaseOperation_isOk() {
        OperationHandler actualOperation =
                operationStrategy.get(PURCHASE_FRUIT_TRANSACTION.getOperation());
        OperationHandler expectedOperation =
                operationHandlerMap.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedOperation, actualOperation);
    }

    @Test
    void getSupplyOperation_isOk() {
        OperationHandler actualOperation =
                operationStrategy.get(SUPPLY_FRUIT_TRANSACTION.getOperation());
        OperationHandler expectedOperation =
                operationHandlerMap.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expectedOperation, actualOperation);
    }

    @Test
    void getReturnOperation_isOk() {
        OperationHandler actualOperation =
                operationStrategy.get(RETURN_FRUIT_TRANSACTION.getOperation());
        OperationHandler expectedOperation =
                operationHandlerMap.get(FruitTransaction.Operation.RETURN);
        assertEquals(expectedOperation, actualOperation);
    }
}
