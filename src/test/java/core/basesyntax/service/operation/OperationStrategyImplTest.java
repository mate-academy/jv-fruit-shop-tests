package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Main;
import core.basesyntax.model.Operation;
import java.util.EnumMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static OperationStrategyImpl strategy;
    private static EnumMap<Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    public static void setUp() {
        operationHandlerMap = new EnumMap<>(Operation.class);
        Main.fillOperationMap(operationHandlerMap);
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_balanceHandlerByBalanceOperation_ok() {
        OperationHandler handler = strategy.get(Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, handler.getClass());
    }

    @Test
    void get_purchaseHandlerByPurchaseOperation_ok() {
        OperationHandler handler = strategy.get(Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, handler.getClass());
    }

    @Test
    void get_returnHandlerByReturnOperation_ok() {
        OperationHandler handler = strategy.get(Operation.RETURN);
        assertEquals(ReturnOperationHandler.class, handler.getClass());
    }

    @Test
    void get_supplyHandlerBySupplyOperation_ok() {
        OperationHandler handler = strategy.get(Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, handler.getClass());
    }

    @Test
    void get_exceptionByNull_notOk() {
        OperationHandler handler = strategy.get(null);
        assertThrows(NullPointerException.class, () -> handler.getOperation());
    }

    @Test
    void get_supplyOperationFromSupplyHandler_ok() {
        SupplyOperationHandler handler = new SupplyOperationHandler();
        assertEquals(Operation.SUPPLY, handler.getOperation());
    }

    @Test
    void get_balanceOperationFromBalanceHandler_ok() {
        BalanceOperationHandler handler = new BalanceOperationHandler();
        assertEquals(Operation.BALANCE, handler.getOperation());
    }

    @Test
    void get_returnOperationFromReturnHandler_ok() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        assertEquals(Operation.RETURN, handler.getOperation());
    }

    @Test
    void get_purchaseOperationFromPurchaseHandler_ok() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        assertEquals(Operation.PURCHASE, handler.getOperation());
    }

    @Test
    void get_operationResultFromPurchaseHandler_ok() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        assertEquals(3, handler.getOperationResult(5, 2));
    }

    @Test
    void get_negativeOperationResultFromPurchaseHandler_ok() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        assertEquals(-1, handler.getOperationResult(1, 2));
    }

    @Test
    void get_operationResultFromBalanceHandler_ok() {
        BalanceOperationHandler handler = new BalanceOperationHandler();
        assertEquals(7, handler.getOperationResult(5, 2));
    }

    @Test
    void get_operationResultFromReturnHandler_ok() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        assertEquals(7, handler.getOperationResult(5, 2));
    }

    @Test
    void get_operationResultFromSupplyHandler_ok() {
        SupplyOperationHandler handler = new SupplyOperationHandler();
        assertEquals(7, handler.getOperationResult(5, 2));
    }
}
