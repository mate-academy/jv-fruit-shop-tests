package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
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
        FruitTransaction incorrectFruiyTransaction = null;
        Exception exception = assertThrows(RuntimeException.class, () -> {
            storageDao.add(incorrectFruiyTransaction);
        });

        String expectedMassage = "Can't add information to storage, information is empty: "
                + incorrectFruiyTransaction;
        String actualMassage = exception.getMessage();

        assertEquals(expectedMassage, actualMassage);

        assertEquals(0, storageDao.getStorage().size());
    }

    @Test
    void addToStorage_isOk() {
        FruitTransaction correctFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        storageDao.add(correctFruitTransaction);
        assertEquals(storageDao.getStorage().size(), 1);
    }

    @Test
    void getValueFromEmptyStorage_expectedException() {
        FruitTransaction correctFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            storageDao.getValue(correctFruitTransaction.getFruit());
        });

        String expectedMassage = "Storage is empty";
        String actualMassage = exception.getMessage();

        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void getValueFromStorage_isOk() {
        FruitTransaction correctFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        storageDao.add(correctFruitTransaction);
        Integer expectedValue = correctFruitTransaction.getQuantity();
        Integer actualValue = storageDao.getValue(correctFruitTransaction.getFruit());

        assertEquals(expectedValue, actualValue);
    }
}
