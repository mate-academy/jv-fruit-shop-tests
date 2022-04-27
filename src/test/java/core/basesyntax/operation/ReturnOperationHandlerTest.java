package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new ReturnOperationHandler(fruitTransactionDao);
    }

    @Test
    void returnHandler_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("orange",15);
        operationHandler.applyNewAmount("orange", 3);
        operationHandler.applyNewAmount("orange", 8);
        Integer actual = Storage.fruitTransactionStorage.get("orange");
        Integer expected = 26;
        Assertions.assertEquals(expected,actual,"Balance from storage " + actual
                + " but should be " + expected);
    }

    @Test
    void returnHandler_inValidValuesPass_NotOk() {
        Storage.fruitTransactionStorage.put("grape", 0);
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount("", 2));
    }

    @Test
    void returnHandler_nullValue_NotOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount(null, null));
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
