package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import org.junit.jupiter.api.Assertions;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static OperationStrategyImpl operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    public void getOperationHandler_validOperation_ok() {
        Assertions.assertEquals(BalanceOperation.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE).getClass(),
                "Expected the handler for BALANCE operation to be BalanceOperation.");

        Assertions. assertEquals(ReturnOperation.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN).getClass(),
                "Expected the handler for RETURN operation to be ReturnOperation.");

        Assertions.assertEquals(PurchaseOperation.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass(),
                "Expected the handler for PURCHASE operation to be PurchaseOperation.");

        Assertions.assertEquals(SupplyOperation.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass(),
                "Expected the handler for SUPPLY operation to be SupplyOperation.");
    }

    @Test
    public void getOperationHandler_nullOperation_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                        operationStrategy.getOperationHandler(null));
    }

    @Test
    public void getOperationHandler_unknownOperation_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                        operationStrategy.getOperationHandler(FruitTransaction.Operation.valueOf("UNKNOWN")));
    }
}
