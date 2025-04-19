package core.basesyntax.operation;

import core.basesyntax.model.Operation;
import core.basesyntax.operation.impl.BalanceHandlerImpl;
import core.basesyntax.operation.impl.PurchaseHandlerImpl;
import core.basesyntax.operation.impl.ReturnHandlerImpl;
import core.basesyntax.operation.impl.SuppliersHandlerImpl;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    
    @BeforeAll
    static void setUp() {
        operationStrategy = new OperationStrategyImpl(Map.of(
                Operation.BALANCE, new BalanceHandlerImpl(),
                Operation.PURCHASE, new PurchaseHandlerImpl(),
                Operation.RETURN, new ReturnHandlerImpl(),
                Operation.SUPPLY, new SuppliersHandlerImpl()));
    }
    
    @Test
    void operationIsNull_ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperationHandler(null));
    }
    
    @Test
    void getOperationHandler_ok() {
        OperationHandler handler = operationStrategy.getOperationHandler(Operation.BALANCE);
        
        Assertions.assertNotNull(handler);
        Assertions.assertInstanceOf(BalanceHandlerImpl.class, handler);
    }
}
