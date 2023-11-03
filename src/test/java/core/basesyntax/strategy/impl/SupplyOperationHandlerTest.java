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

class SupplyOperationHandlerTest {
    private static FruitStorageDao fruitStorageDao;
    private SupplyOperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        supplyOperationHandler = new SupplyOperationHandler(fruitStorageDao);
    }

    @Test
    void operate_withValidData_ok() {
        fruitStorageDao.add("apple", 20);
        supplyOperationHandler.operate("apple", 10);
        assertEquals(30, fruitStorageDao.getQuantity("apple"));
    }

    @Test
    void operate_withZeroQuantity_notOk() {
        fruitStorageDao.add("banana", 5);
        supplyOperationHandler.operate("banana", 0);
        assertEquals(5, fruitStorageDao.getQuantity("banana"));
    }

    @Test
    void operate_withNegativeQuantity_notOk() {
        fruitStorageDao.add("banana", 15);
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.operate("banana", -5));
        assertEquals("You can't supply a negative quantity of fruits!", exception.getMessage());
        assertEquals(15, fruitStorageDao.getQuantity("banana"));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }
}
