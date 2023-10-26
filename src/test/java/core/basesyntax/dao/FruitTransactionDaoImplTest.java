package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionDaoImplTest {
    private static final String APPLE = "apple";
    private static FruitTransactionDao fruitTransactionDao;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
    }

    @Test
    void add_toStorage_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, APPLE, 75);
        Storage.fruitTransactions.add(fruitTransaction);
        assertEquals(fruitTransaction, fruitTransactionDao.getFromStorage(fruitTransaction));
    }

    @Test
    void get_nullFromStorage_isNotOk() {
        assertNull(fruitTransactionDao.getFromStorage(null));
    }

    @Test
    void get_nonExistFruitFromStorage_isNotOk() {
        assertNull(fruitTransactionDao
                .getFromStorage(FruitTransaction.of(Operation.RETURN, APPLE, 20)));
    }

    @Test
    void add_toStorageByDao_isOk() {
        fruitTransactionDao
                .addToStorage(FruitTransaction.of(Operation.BALANCE, APPLE, 40));
        FruitTransaction actual = Storage.fruitTransactions.get(0);
        FruitTransaction expected = FruitTransaction.of(Operation.BALANCE, APPLE, 40);
        assertEquals(expected, actual);
    }

    @Test
    void add_nullToStorage_isNotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fruitTransactionDao.addToStorage(null));
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactions.clear();
    }
}
