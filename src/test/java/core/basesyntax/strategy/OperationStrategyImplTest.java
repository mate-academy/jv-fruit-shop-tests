package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.Constants;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final String BANANA = Constants.BANANA;
    private final OperationHandler expectedHandler = new BalanceOperationHandler();
    private final FruitTransaction transactionOk =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 10);
    private final FruitTransaction transactionNotOk =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 10);
    private OperationStrategy strategy;
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());

    @BeforeEach
    void setUp() {
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getHandler_Ok() {
        OperationHandler actualHandler = strategy.getHandler(transactionOk);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void getHandler_absentHandler_notOk() {
        OperationHandler actualHandler = strategy.getHandler(transactionNotOk);
        assertNull(actualHandler);
    }
}
