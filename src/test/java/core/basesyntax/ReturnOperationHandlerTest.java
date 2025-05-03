package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.model.Operations;
import core.basesyntax.basesyntax.service.strategy.ReturnOperationHandler;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {

    @Test
    void get_operationReturnFromReturnHandler_Ok() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        assertEquals(Operations.RETURN, handler.getOperation());
    }

    @Test
    void apply_operationFromReturnHandler_Ok() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        assertEquals(11, handler.applyOperation(7, 4));
    }
}
