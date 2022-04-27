package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransactionDao fruitTransactionDao;
    private static Map<FruitTransaction.Operation,OperationHandler> operationHandlerMap;
    private static final String VALID_FIRST_OPERATION = "b";
    private static final String VALID_SECOND_OPERATION = "p";
    private static final String VALID_THIRD_OPERATION = "r";
    private static final String VALID_FOURTH_OPERATION = "s";
    private static final String INVALID_FIRST_OPERATION = "m";

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void operationStrategy_passValidSymbol_Ok() {
        OperationHandler first = operationStrategy.getOperationHandler(VALID_FIRST_OPERATION);
        Assertions.assertTrue(operationHandlerMap.containsValue(first));
        OperationHandler second = operationStrategy.getOperationHandler(VALID_SECOND_OPERATION);
        Assertions.assertTrue(operationHandlerMap.containsValue(second));
        OperationHandler third = operationStrategy.getOperationHandler(VALID_THIRD_OPERATION);
        Assertions.assertTrue(operationHandlerMap.containsValue(third));
        OperationHandler fourth = operationStrategy.getOperationHandler(VALID_FOURTH_OPERATION);
        Assertions.assertTrue(operationHandlerMap.containsValue(fourth));
    }

    @Test
    void operationStrategy_passInvalidSymbol_NotOk() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> operationStrategy.getOperationHandler(INVALID_FIRST_OPERATION));
    }

    @Test
    void operationStrategy_nullValue_not0k() {
        assertThrows(RuntimeException.class,()
                -> operationStrategy.getOperationHandler(null));
    }

    @AfterAll
    static void afterAll() {
        operationHandlerMap.clear();
    }
}
