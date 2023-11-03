package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private FruitStorageDao fruitStorageDao;
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitStorageDao);
    }

    @Test
    void operate_withValidData_ok() {
        fruitStorageDao.add("apple", 20);
        assertEquals(20, fruitStorageDao.getQuantity("apple"));
    }

    @Test
    void operate_withZeroQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperationHandler.operate("banana", 0));
    }

    @Test
    void operate_withNegativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperationHandler.operate("banana", -10));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }
}
