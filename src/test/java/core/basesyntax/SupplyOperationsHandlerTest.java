package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.model.Operations;
import core.basesyntax.basesyntax.service.operations.SupplyOperationsHandler;
import org.junit.jupiter.api.Test;

public class SupplyOperationsHandlerTest {

    @Test
    void get_operationSupplyFromSupplyHandler_Ok() {
        SupplyOperationsHandler handler = new SupplyOperationsHandler();
        assertEquals(Operations.SUPPLY, handler.getOperation());
    }

    @Test
    void apply_operationFromSupplyHandler_Ok() {
        SupplyOperationsHandler handler = new SupplyOperationsHandler();
        assertEquals(11, handler.applyOperation(7, 4));
    }
}

