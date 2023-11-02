package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseOperationHandlerTest {
    private FruitStorageDao fruitStorageDao;
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitStorageDao);
    }

    @Test
    void operate_withValidData_ok() {
        fruitStorageDao.add("apple", 20);
        purchaseOperationHandler.operate("apple", 10);
        assertEquals(10, fruitStorageDao.getQuantity("apple"));
    }

    @Test
    void operate_withInsufficientQuantity() {
        fruitStorageDao.add("banana", 5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.operate("banana", 10));
        assertEquals("You can't buy this amount of fruits!", exception.getMessage());
        assertEquals(5, fruitStorageDao.getQuantity("banana"));
    }
}