package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.handler.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void handle_noHandlerFor_throwsException() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlers);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 20);

        Map<String, Integer> storage = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> strategy.handle(transaction, storage));
    }
}
