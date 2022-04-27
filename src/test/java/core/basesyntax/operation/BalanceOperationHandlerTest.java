package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new BalanceOperationHandler(fruitTransactionDao);
    }

    @Test
    void balanceHandler_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("banana",0);
        Integer actual = operationHandler.applyNewAmount("banana", 20);
        Integer expected = 20;
        Assertions.assertEquals(expected,actual,"Balance from storage " + actual
                + " but should be " + expected);
    }

    @Test
    void balanceHandler_inValidValuesPass_NotOk() {
        Storage.fruitTransactionStorage.put("banana", 0);
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount("", 20));
    }

    @Test
    void balanceHandler_nullValue_NotOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount(null, null));
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
