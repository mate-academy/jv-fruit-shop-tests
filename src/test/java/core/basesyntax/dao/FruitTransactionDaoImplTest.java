package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FruitTransactionDaoImplTest {
 private FruitTransactionDao fruitTransactionDao;
 private static final String APPLE = "apple";

    @BeforeEach
    void beforeEach() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
    }

    @Test
    void getFromStorage_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.RETURN, APPLE, 50);
        fruitTransactionDao
                .addToStorage(fruitTransaction);
        assertNotNull(fruitTransactionDao.getFromStorage(fruitTransaction));
    }

    @Test
    void getNullFromStorage_isNotOk() {
        assertNull(fruitTransactionDao.getFromStorage(null));
    }

    @Test
    void getNonExistFruitFromStorage_isNotOk() {
        assertNull(fruitTransactionDao
                .getFromStorage(FruitTransaction.of(Operation.RETURN, APPLE, 20)));
    }

    @Test
    void addExistFileToStorage_isOk() {
        fruitTransactionDao
                .addToStorage(FruitTransaction.of(Operation.BALANCE, APPLE, 40));
        FruitTransaction actual = Storage.fruitTransactions.get(0);
        FruitTransaction expected = FruitTransaction.of(Operation.BALANCE, APPLE, 40);
        assertEquals(expected, actual);
    }

    @Test
    void addNullToStorage_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> fruitTransactionDao.addToStorage(null));
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactions.clear();
    }
}