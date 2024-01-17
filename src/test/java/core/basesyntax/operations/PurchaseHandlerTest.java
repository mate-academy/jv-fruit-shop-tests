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
    private static final FruitTransaction BALANCE_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
    private static final FruitTransaction CORRECT_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30);
    private static final FruitTransaction NULL_FRUIT_TRANSACTION = null;
    private static final FruitTransaction FRUIT_WITH_INCORRECT_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 110);
    private static final FruitTransaction FRUIT_WITH_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -10);
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseHandler();
    }

    @BeforeEach
    void setUp() {
        storageDao.add(BALANCE_FRUIT_TRANSACTION);
    }

    @AfterEach
    void tearDown() {
        storageDao.getStorage().clear();
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
    void getHandlerWithIncorrectQuantity_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(FRUIT_WITH_INCORRECT_QUANTITY);
        });
        String expectedMassage = "Balance of "
                + FRUIT_WITH_INCORRECT_QUANTITY.getFruit()
                + " is less then "
                + FRUIT_WITH_INCORRECT_QUANTITY.getQuantity();
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void getHandlerWithNegativeQuantity_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.getHandler(FRUIT_WITH_NEGATIVE_QUANTITY);
        });
        String expectedMassage = "Fruit quantity can't be negative "
                + FRUIT_WITH_NEGATIVE_QUANTITY.getQuantity();
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }
}
