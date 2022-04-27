package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new SupplyOperationHandler(fruitTransactionDao);
    }

    @Test
    void supplyHandler_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("kiwi",5);
        operationHandler.applyNewAmount("kiwi", 25);
        operationHandler.applyNewAmount("kiwi", 13);
        operationHandler.applyNewAmount("kiwi", 6);
        Integer actual = Storage.fruitTransactionStorage.get("kiwi");
        Integer expected = 49;
        Assertions.assertEquals(expected,actual,"Balance from storage " + actual
                + " but should be " + expected);
    }

    @Test
    void supplyHandler_inValidValuesPass_NotOk() {
        Storage.fruitTransactionStorage.put("grape", 0);
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount("", 2));
    }

    @Test
    void supplyHandler_nullValue_NotOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount(null, null));
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
