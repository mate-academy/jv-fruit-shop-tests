package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.BalanceOperation;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationHandlerStrategyImplTest {
    private static final int QUANTITY = 20;
    private static final String FRUIT = "banana";
    private static OperationHandlerStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlersMap;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        handlersMap = new HashMap<>();
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        strategy = new OperationHandlerStrategyImpl(handlersMap);

        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(QUANTITY);
    }

    @Test
    void getOperationHandler_FromTransaction_Ok() {
        OperationHandler expected = handlersMap.get(FruitTransaction.Operation.BALANCE);
        OperationHandler actual = strategy.getOperationHandler(transaction);
        assertEquals(expected, actual);
    }

    @Test
    void getOperationHandler_operationIsNull_NotOk() {
        OperationHandler operationHandler = strategy.getOperationHandler(new FruitTransaction());
        assertNull(operationHandler);
    }

    @Test
    void getOperationHandler_transactionIsNull_NotOk() {
        assertThrows(NullPointerException.class, () -> strategy.getOperationHandler(null));
    }
}
