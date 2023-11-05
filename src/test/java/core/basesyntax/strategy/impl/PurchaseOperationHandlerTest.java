package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static FruitStorageDao fruitStorageDao;
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler(fruitStorageDao);
    }

    @Test
    void operate_withValidData_ok() {
        fruitStorageDao.add("apple", 20);
        purchaseOperationHandler.operate("apple", 10);
        assertEquals(10, fruitStorageDao.getQuantity("apple"));
    }

    @Test
    void operate_withInsufficientQuantity_notOk() {
        fruitStorageDao.add("banana", 5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.operate("banana", 10));
        assertEquals("You can't buy this amount of fruits!", exception.getMessage());
        assertEquals(5, fruitStorageDao.getQuantity("banana"));
    }

    @Test
    void operate_withZeroQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperationHandler.operate("apple", 0));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }
}
