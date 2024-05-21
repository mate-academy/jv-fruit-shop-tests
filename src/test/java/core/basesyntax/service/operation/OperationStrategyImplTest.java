package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Main;
import core.basesyntax.model.Operation;
import java.util.EnumMap;
import java.util.Map;
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
    void get_handlerByOperation_ok() {
        Map<Operation, Class<? extends OperationHandler>> operationHandlerMap = Map.of(
                Operation.BALANCE, BalanceOperationHandler.class,
                Operation.PURCHASE, PurchaseOperationHandler.class,
                Operation.RETURN, ReturnOperationHandler.class,
                Operation.SUPPLY, SupplyOperationHandler.class
        );

        for (Map.Entry<Operation, Class<? extends OperationHandler>> entry
                : operationHandlerMap.entrySet()) {
            Operation operation = entry.getKey();
            Class<? extends OperationHandler> expectedHandlerClass = entry.getValue();

            OperationHandler handler = strategy.get(operation);
            assertEquals(expectedHandlerClass, handler.getClass());
        }
    }

    @Test
    void get_operationFromHandler_ok() {
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler()
        );

        for (Map.Entry<Operation, OperationHandler> entry : operationHandlerMap.entrySet()) {
            Operation expectedOperation = entry.getKey();
            Operation operation = entry.getValue().getOperation();

            assertEquals(operation, expectedOperation);
        }

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
    void get_exceptionByNull_notOk() {
        OperationHandler handler = strategy.get(null);
        assertThrows(NullPointerException.class, () -> handler.getOperation());
    }

    @Test
    void get_operationResultFromPurchaseHandler_ok() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        assertEquals(3, handler.getOperationResult(5, 2));
    }

    @Test
    void get_negativeOperationResultFromPurchaseHandler_notOk() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        assertThrows(RuntimeException.class, () -> handler.getOperationResult(1, 2));
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
