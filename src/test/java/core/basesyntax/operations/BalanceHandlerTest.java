package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceHandler();
    }

    @Test
    void getHandler_isOk() {
        FruitTransaction correctFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        Integer actualValue = operationHandler.getHandler(correctFruitTransaction);
        Integer expectedValue = storageDao.getValue(correctFruitTransaction.getFruit());
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getHandlerWithNullOperation_expectedException() {
        FruitTransaction nullFruitTransaction = null;
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(nullFruitTransaction);
        });
        String expectedMassage = "Fruit transaction is null "
                + nullFruitTransaction;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void getHandlerWithIncorrectOperation_expectedException() {
        FruitTransaction incorrectFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(incorrectFruitTransaction);
        });
        String expectedMassage = "Can' find balance "
                + incorrectFruitTransaction;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }
}
