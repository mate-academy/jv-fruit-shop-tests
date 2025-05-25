package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private OperationHandler operationHandler;

    @Test
    void getOperation_null_notOk() {
        operationHandler = new BalanceHandler();
        assertThrows(IllegalArgumentException.class, () -> operationHandler.getOperation(null));
    }
}
