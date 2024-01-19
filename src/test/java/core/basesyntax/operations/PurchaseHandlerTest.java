package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseHandler();
    }

    @BeforeEach
    void setUp() {
        storageDao.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                100));
    }

    @AfterEach
    void tearDown() {
        storageDao.getStorage().clear();
    }

    @Test
    void getHandler_isOk() {
        FruitTransaction correctFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana",
                        30);
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
    void getHandlerWithIncorrectQuantity_expectedException() {
        FruitTransaction fruitWithIncorrectQuantity =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana",
                        110);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(fruitWithIncorrectQuantity);
        });
        String expectedMassage = "Balance of "
                + fruitWithIncorrectQuantity.getFruit()
                + " is less then "
                + fruitWithIncorrectQuantity.getQuantity();
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void getHandlerWithNegativeQuantity_expectedException() {
        FruitTransaction fruitWithNegativeQuantity =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana",
                        -10);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(fruitWithNegativeQuantity);
        });
        String expectedMassage = "Fruit quantity can't be negative "
                + fruitWithNegativeQuantity.getQuantity();
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }
}
