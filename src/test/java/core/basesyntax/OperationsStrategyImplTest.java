package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.model.Operations;
import core.basesyntax.basesyntax.service.operations.BalanceOperationsHandler;
import core.basesyntax.basesyntax.service.operations.PurchaseOperationsHandler;
import core.basesyntax.basesyntax.service.operations.SupplyOperationsHandler;
import core.basesyntax.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.basesyntax.service.strategy.OperationsStrategyImpl;
import core.basesyntax.basesyntax.service.strategy.ReturnOperationHandler;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationsStrategyImplTest {
<<<<<<< HEAD

    private OperationsStrategyImpl strategy;

    @BeforeEach
    public void setUp() {
        Map<Operations, OperationHandler> operationHandlerMap = new EnumMap<>(Operations.class);
=======
    private static OperationsStrategyImpl strategy;
    private static Map<Operations, OperationHandler> operationHandlerMap;

    @BeforeEach
    public void setUp() {
        operationHandlerMap = new EnumMap<>(Operations.class);
>>>>>>> origin/main
        operationHandlerMap.put(Operations.BALANCE, new BalanceOperationsHandler());
        operationHandlerMap.put(Operations.SUPPLY, new SupplyOperationsHandler());
        operationHandlerMap.put(Operations.PURCHASE, new PurchaseOperationsHandler());
        operationHandlerMap.put(Operations.RETURN, new ReturnOperationHandler());
        strategy = new OperationsStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_exceptionNull_NotOk() {
        assertThrows(UnsupportedOperationException.class, () -> {
            OperationHandler handler = strategy.get(null);
            handler.getOperation();
        });
    }

    @Test
    void get_supplyHandlerBySupplyOperation_Ok() {
        OperationHandler handler = strategy.get(Operations.SUPPLY);
        assertEquals(SupplyOperationsHandler.class, handler.getClass());
    }

    @Test
    void get_purchaseHandlerByPurchaseOperation_Ok() {
        OperationHandler handler = strategy.get(Operations.PURCHASE);
        assertEquals(PurchaseOperationsHandler.class, handler.getClass());
    }

    @Test
    void get_balanceHandlerByBalanceOperation_Ok() {
        OperationHandler handler = strategy.get(Operations.BALANCE);
        assertEquals(BalanceOperationsHandler.class, handler.getClass());
    }

    @Test
<<<<<<< HEAD
    void get_returnHandlerByReturnOperation_Ok() {
        OperationHandler handler = strategy.get(Operations.RETURN);
        assertEquals(ReturnOperationHandler.class, handler.getClass());
    }
}

=======
    void get_returnHandlerByBalanceOperation_Ok() {
        OperationHandler handler = strategy.get(Operations.RETURN);
        assertEquals(ReturnOperationHandler.class, handler.getClass());
    }

    @Test
    void get_operationSypplyFromSypplyHandler_Ok() {
        SupplyOperationsHandler handler = new SupplyOperationsHandler();
        assertEquals(Operations.SUPPLY, handler.getOperation());
    }

    @Test
    void get_operationPurchaseFromPurchaseHandler_Ok() {
        PurchaseOperationsHandler handler = new PurchaseOperationsHandler();
        assertEquals(Operations.PURCHASE, handler.getOperation());
    }

    @Test
    void get_operationBalanceFromBalanceHandler_Ok() {
        BalanceOperationsHandler handler = new BalanceOperationsHandler();
        assertEquals(Operations.BALANCE, handler.getOperation());
    }

    @Test
    void get_operationReturnFromReturnHandler_Ok() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        assertEquals(Operations.RETURN, handler.getOperation());
    }

    @Test
    void apply_operationFromSupplyHandler_Ok() {
        SupplyOperationsHandler handler = new SupplyOperationsHandler();
        assertEquals(11, handler.applyOperation(7, 4));
    }

    @Test
    void apply_operationFromPurchaseHandler_Ok() {
        PurchaseOperationsHandler handler = new PurchaseOperationsHandler();
        assertEquals(3, handler.applyOperation(7, 4));
    }

    @Test
    void apply_operationFromReturnHandler_Ok() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        assertEquals(11, handler.applyOperation(7, 4));
    }

    @Test
    void apply_operationFromBalanceHandler_Ok() {
        BalanceOperationsHandler handler = new BalanceOperationsHandler();
        assertEquals(11, handler.applyOperation(7, 4));
    }
}
>>>>>>> origin/main
