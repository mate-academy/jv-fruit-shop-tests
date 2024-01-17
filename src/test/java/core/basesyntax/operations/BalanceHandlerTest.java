package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final FruitTransaction CORRECT_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
    private static final FruitTransaction NULL_FRUIT_TRANSACTION = null;
    private static final FruitTransaction INCORRECT_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceHandler();
    }

    @Test
    void getHandler_isOk() {
        Integer actualValue = operationHandler.getHandler(CORRECT_FRUIT_TRANSACTION);
        Integer expectedValue = storageDao.getValue(CORRECT_FRUIT_TRANSACTION.getFruit());
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getHandlerWithNullOperation_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(NULL_FRUIT_TRANSACTION);
        });
        String expectedMassage = "Fruit transaction is null "
                + NULL_FRUIT_TRANSACTION;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void getHandlerWithIncorrectOperation_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(INCORRECT_FRUIT_TRANSACTION);
        });
        String expectedMassage = "Can' find balance "
                + INCORRECT_FRUIT_TRANSACTION;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }
}
