package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final Map<Operation, OperationHandler> operationHandlerMap = Map.of(
            Operation.SUPPLY, new SupplyHandler(),
            Operation.BALANCE, new BalanceHandler(),
            Operation.RETURN, new ReturnHandler(),
            Operation.PURCHASE, new PurchaseHandler()
    );

    @Test
    void get_normalRecogniseMode_Ok() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        Class<? extends OperationHandler> actual
                = operationStrategy.get(Operation.BALANCE).getClass();
        Class<? extends OperationHandler> expected
                = operationHandlerMap.get(Operation.BALANCE).getClass();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void check_nullPointerOnMap_Ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> new OperationStrategyImpl(null));
    }
}
