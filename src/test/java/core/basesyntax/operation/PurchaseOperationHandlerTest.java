package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitTransactionDao);
    }

    @Test
    void purchaseHandler_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("apple",100);
        operationHandler.applyNewAmount("apple", 20);
        operationHandler.applyNewAmount("apple", 20);
        operationHandler.applyNewAmount("apple", 20);
        operationHandler.applyNewAmount("apple", 30);
        Integer actual = Storage.fruitTransactionStorage.get("apple");
        Integer expected = 10;
        Assertions.assertEquals(expected,actual,"Balance from storage " + actual
                + " but should be " + expected);
    }

    @Test
    void purchaseHandler_inValidValuesPass_NotOk() {
        Storage.fruitTransactionStorage.put("banana", 0);
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount("", 15));
    }

    @Test
    void purchaseHandler_nullValue_NotOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.applyNewAmount(null, null));
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
