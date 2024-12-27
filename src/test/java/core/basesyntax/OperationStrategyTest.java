package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private OperationStrategyImpl operationStrategy;
    private BalanceOperation balanceOperation;
    private PurchaseOperation purchaseOperation;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
        supplyOperation = new SupplyOperation();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceOperation);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseOperation);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyOperation);
        operationHandlers.put(FruitTransaction.Operation.RETURN, supplyOperation);

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getOperationHandler_ok() {
        assertEquals(balanceOperation, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE));
        assertEquals(purchaseOperation, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE));
        assertEquals(supplyOperation, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY));
        assertEquals(supplyOperation, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void getOperationHandlerNotMapped_returnsNull() {
        Map<FruitTransaction.Operation, OperationHandler> emptyOperationHandlers = new HashMap<>();
        OperationStrategyImpl fakeOperationStrategy
                = new OperationStrategyImpl(emptyOperationHandlers);
        assertNull(fakeOperationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void getOperationHandlerNull_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getOperationHandler(null));
    }
}
