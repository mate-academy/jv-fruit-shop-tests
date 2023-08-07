package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseHandler;
import core.basesyntax.handlers.ReturnHandler;
import core.basesyntax.handlers.SupplyHandler;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceiveHandlerImplTest {
    private ReceiveHandlerImpl receiveHandler;

    @BeforeEach
    void setUp() {
        receiveHandler = new ReceiveHandlerImpl();
    }

    @Test
    void receive_BalanceHandler_ok() {
        OperationHandler actual = receiveHandler.getHandler(Operation.BALANCE);
        assertTrue(actual instanceof BalanceHandler);
    }

    @Test
    void receive_PurchaseHandler_ok() {
        OperationHandler actual = receiveHandler.getHandler(Operation.PURCHASE);
        assertTrue(actual instanceof PurchaseHandler);
    }

    @Test
    void receive_ReturnHandler_ok() {
        OperationHandler actual = receiveHandler.getHandler(Operation.RETURN);
        assertTrue(actual instanceof ReturnHandler);
    }

    @Test
    void receive_SupplyHandler_ok() {
        OperationHandler actual = receiveHandler.getHandler(Operation.SUPPLY);
        assertTrue(actual instanceof SupplyHandler);
    }
}
