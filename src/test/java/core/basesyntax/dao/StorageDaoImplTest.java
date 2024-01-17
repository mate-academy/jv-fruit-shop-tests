package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final FruitTransaction INCORRECT_FRUIT_TRANSACTION = null;
    private static final FruitTransaction CORRECT_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        storageDao.getStorage().clear();
    }

    @Test
    void addIncorrectFruitToStorage_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            storageDao.add(INCORRECT_FRUIT_TRANSACTION);
        });

        String expectedMassage = "Can't add information to storage, information is empty: "
                + INCORRECT_FRUIT_TRANSACTION;
        String actualMassage = exception.getMessage();

        assertEquals(expectedMassage, actualMassage);

        assertEquals(0, storageDao.getStorage().size());
    }

    @Test
    void addToStorage_isOk() {
        storageDao.add(CORRECT_FRUIT_TRANSACTION);
        assertEquals(storageDao.getStorage().size(), 1);
    }

    @Test
    void getValueFromEmptyStorage_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            storageDao.getValue(CORRECT_FRUIT_TRANSACTION.getFruit());
        });

        String expectedMassage = "Storage is empty";
        String actualMassage = exception.getMessage();

        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void getValueFromStorage_isOk() {
        storageDao.add(CORRECT_FRUIT_TRANSACTION);
        Integer expectedValue = CORRECT_FRUIT_TRANSACTION.getQuantity();
        Integer actualValue = storageDao.getValue(CORRECT_FRUIT_TRANSACTION.getFruit());

        assertEquals(expectedValue, actualValue);
    }
}
