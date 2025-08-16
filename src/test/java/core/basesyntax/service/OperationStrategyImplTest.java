package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy strategy;
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperation();
        supplyHandler = new SupplyOperation();

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);

        strategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void getHandler_existingOperation_returnsCorrectHandler() {
        OperationHandler result = strategy.getHandler(FruitTransaction.Operation.BALANCE);

        assertEquals(balanceHandler, result);
        assertNotNull(result);
    }

    @Test
    void getHandler_anotherExistingOperation_returnsCorrectHandler() {
        OperationHandler result = strategy.getHandler(FruitTransaction.Operation.SUPPLY);

        assertEquals(supplyHandler, result);
        assertNotNull(result);
    }

    @Test
    void getHandler_nonExistingOperation_throwsException() {
        assertThrows(RuntimeException.class,
                () -> strategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void getHandler_allConfiguredOperations_returnsHandlers() {
        assertNotNull(strategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertNotNull(strategy.getHandler(FruitTransaction.Operation.SUPPLY));
    }
}
