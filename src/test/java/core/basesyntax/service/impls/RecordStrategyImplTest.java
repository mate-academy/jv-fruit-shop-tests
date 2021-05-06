package core.basesyntax.service.impls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.RecordStrategy;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperationHandler;
import core.basesyntax.service.handler.ReturnOperationHandler;
import core.basesyntax.service.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecordStrategyImplTest {
    private static final String BALANCE_OPERATION = "b";
    private static final String SUPPLY_OPERATION = "s";
    private static final String PURCHASE_OPERATION = "p";
    private static final String RETURN_OPERATION = "r";
    private static final String UNKNOWN_OPERATION = "u";
    private static RecordStrategy recordStrategy;

    @BeforeAll
    public static void beforeAll() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE_OPERATION, new BalanceOperationHandler());
        operationHandlerMap.put(SUPPLY_OPERATION, new SupplyOperationHandler());
        operationHandlerMap.put(PURCHASE_OPERATION, new PurchaseOperationHandler());
        operationHandlerMap.put(RETURN_OPERATION, new ReturnOperationHandler());
        recordStrategy = new RecordStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_BalanceOperationHandler_isOk() {
        Optional<OperationHandler> balanceOperationHandler = recordStrategy.get(BALANCE_OPERATION);
        assertEquals(BalanceOperationHandler.class, balanceOperationHandler.get().getClass());
    }

    @Test
    public void get_SupplyOperationHandler_isOk() {
        Optional<OperationHandler> supplyOperationHandler = recordStrategy.get(SUPPLY_OPERATION);
        assertEquals(SupplyOperationHandler.class, supplyOperationHandler.get().getClass());
    }

    @Test
    public void get_PurchaseOperationHandler_isOk() {
        Optional<OperationHandler> purchaseOperationHandler
                = recordStrategy.get(PURCHASE_OPERATION);
        assertEquals(PurchaseOperationHandler.class, purchaseOperationHandler.get().getClass());
    }

    @Test
    public void get_ReturnOperationHandler_isOk() {
        Optional<OperationHandler> returnOperationHandler = recordStrategy.get(RETURN_OPERATION);
        assertEquals(ReturnOperationHandler.class, returnOperationHandler.get().getClass());
    }

    @Test
    public void get_OperationsHandler_isNotOK() {
        Optional<OperationHandler> balanceOperationHandler = recordStrategy.get(BALANCE_OPERATION);
        Optional<OperationHandler> supplyOperationHandler
                = recordStrategy.get(SUPPLY_OPERATION);
        assertNotEquals(supplyOperationHandler.get().getClass(),
                balanceOperationHandler.get().getClass());
    }

    @Test
    public void get_UnknownHandler_isNotOK() {
        Optional<OperationHandler> unknownOperationHandler = recordStrategy.get(UNKNOWN_OPERATION);
        assertThrows(NoSuchElementException.class, unknownOperationHandler::get);
    }
}
