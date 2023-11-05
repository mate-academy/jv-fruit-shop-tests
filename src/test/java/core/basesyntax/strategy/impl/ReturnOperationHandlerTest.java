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

class ReturnOperationHandlerTest {
    private static FruitStorageDao fruitStorageDao;
    private ReturnOperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandler(fruitStorageDao);
    }

    @Test
    void operate_withValidData_ok() {
        fruitStorageDao.add("apple", 20);
        returnOperationHandler.operate("apple", 10);
        assertEquals(30, fruitStorageDao.getQuantity("apple"));
    }

    @Test
    void operate_withNegativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.operate("banana", -10));
    }

    @Test
    void operate_withZeroQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.operate("banana", 0));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }
}
