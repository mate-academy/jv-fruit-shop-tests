package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FruitTransactionException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionValidation;
import core.basesyntax.service.impl.FruitTransactionValidationImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    public static final String DEFAULT_FRUIT = "banana";
    public static final int DEFAULT_QUANTITY = 100;
    private FruitTransaction fruitTransaction;
    private final FruitTransactionValidation transactionValidation
            = new FruitTransactionValidationImpl();
    private final StorageDao storageDao = new StorageDaoImpl(transactionValidation);

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void addValidTransaction_Ok() {
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        storageDao.add(fruitTransaction);
        assertTrue(Storage.storage.containsKey(DEFAULT_FRUIT));

    }

    @Test
    void addTwoTransactions_Ok() {
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        String fruitName = "apple";
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setFruit(fruitName);
        fruitTransaction2.setQuantity(DEFAULT_QUANTITY);
        storageDao.add(fruitTransaction);
        storageDao.add(fruitTransaction2);
        int expected = 2;
        int actual = Storage.storage.size();
        assertEquals(expected, actual);
    }

    @Test
    void addNullTransaction_NotOk() {
        assertThrows(FruitTransactionException.class, () -> storageDao.add(fruitTransaction));
    }

    @Test
    void addNullKeyTransaction_NotOK() {
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        assertThrows(FruitTransactionException.class, () -> storageDao.add(fruitTransaction));
    }

    @Test
    void addNullValueTransaction_NotOk() {
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        assertThrows(FruitTransactionException.class, () -> storageDao.add(fruitTransaction));
    }

    @Test
    void getValidTransaction_Ok() {
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        storageDao.add(fruitTransaction);
        int actual = storageDao.get(fruitTransaction);
        assertEquals(DEFAULT_QUANTITY, actual);
        assertDoesNotThrow(() -> storageDao.add(fruitTransaction));
    }

    @Test
    void getNullTransaction_NotOk() {
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        assertThrows(FruitTransactionException.class, () -> storageDao.get(null));
    }

    @Test
    void getNullKeyTransaction_NotOk() {
        assertThrows(FruitTransactionException.class, () -> storageDao.get(fruitTransaction));
    }

    @Test
    void getStorage_Ok() {
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        storageDao.add(fruitTransaction);
        System.out.println(storageDao.getStorage());
        assertTrue(Storage.storage.containsKey(DEFAULT_FRUIT));
    }

    @Test
    void getEmptyStorage_Ok() {
        assertTrue(storageDao.getStorage().isEmpty());
    }
}
