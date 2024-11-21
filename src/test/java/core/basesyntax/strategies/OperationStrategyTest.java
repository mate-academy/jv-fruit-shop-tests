package core.basesyntax.strategies;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.operationhandler.BalanceOperationHandler;
import core.basesyntax.services.operationhandler.IOperationHandler;
import core.basesyntax.services.operationhandler.PurchaseOperationHandler;
import core.basesyntax.services.operationhandler.ReturnOperationHandler;
import core.basesyntax.services.operationhandler.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static IOperationStrategy operationStrategy;

    @BeforeAll
    public static void beforeAll() {
        Map<FruitTransaction.Operation, IOperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(),

                FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(),

                FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(),

                FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler()
        );

        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void get_shouldReturnBalanceOperationHandler_forBalanceOperation() {
        IOperationHandler operationHandler = operationStrategy
                .get(FruitTransaction.Operation.BALANCE);

        Assertions.assertInstanceOf(
                BalanceOperationHandler.class,
                operationHandler,
                "Should return BalanceOperationHandler for balance operation"
        );
    }

    @Test
    public void get_shouldReturnPurchaseOperationHandler_forPurchaseOperation() {
        IOperationHandler operationHandler = operationStrategy
                .get(FruitTransaction.Operation.PURCHASE);

        Assertions.assertInstanceOf(
                PurchaseOperationHandler.class,
                operationHandler,
                "Should return PurchaseOperationHandler for purchase operation"
        );
    }

    @Test
    public void get_shouldReturnReturnOperationHandler_forReturnOperation() {
        IOperationHandler operationHandler = operationStrategy
                .get(FruitTransaction.Operation.RETURN);

        Assertions.assertInstanceOf(
                ReturnOperationHandler.class,
                operationHandler,
                "Should return ReturnOperationHandler for return operation"
        );
    }

    @Test
    public void get_shouldReturnSupplyOperationHandler_forSupplyOperation() {
        IOperationHandler operationHandler = operationStrategy
                .get(FruitTransaction.Operation.SUPPLY);

        Assertions.assertInstanceOf(
                SupplyOperationHandler.class,
                operationHandler,
                "Should return SupplyOperationHandler for supply operation"
        );
    }
}
