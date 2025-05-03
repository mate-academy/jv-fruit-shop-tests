package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.model.Operations;
import core.basesyntax.basesyntax.service.operations.BalanceOperationsHandler;
import org.junit.jupiter.api.Test;

public class BalanceOperationsHandlerTest {

    @Test
    void get_operationBalanceFromBalanceHandler_Ok() {
        BalanceOperationsHandler handler = new BalanceOperationsHandler();
        assertEquals(Operations.BALANCE, handler.getOperation());
    }

    @Test
    void apply_operationFromBalanceHandler_Ok() {
        BalanceOperationsHandler handler = new BalanceOperationsHandler();
        assertEquals(11, handler.applyOperation(7, 4));
    }
}
