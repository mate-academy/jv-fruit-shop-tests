package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitOperationDaoImplTest {
    private FruitOperationDao fruitOperationDao;

    @BeforeEach
    void setUp() {
        fruitOperationDao = new FruitOperationDaoImpl();
        Storage.SHOP_STORE.clear();
    }

    @Test
    void testAddAndGetFruit() {
        FruitOperation apple = new FruitOperation(FruitOperation.Operation.BALANCE, "apple", 10);
        fruitOperationDao.add(apple);

        Optional<FruitOperation> result = fruitOperationDao.get("apple");
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getQuantity());
    }

    @Test
    void testAddNotNullFruit() {
        FruitOperation orange = new FruitOperation(FruitOperation.Operation.SUPPLY,"orange",15);
        fruitOperationDao.add(orange);

        Optional<FruitOperation> result = fruitOperationDao.get("orange");
        assertNotNull(result);
    }

    @Test
    void testGetNonExistingFruit() {
        Optional<FruitOperation> result = fruitOperationDao.get("banana");
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateExistingFruit() {
        FruitOperation apple = new FruitOperation(FruitOperation.Operation.BALANCE, "apple", 10);
        fruitOperationDao.add(apple);

        FruitOperation updatedApple = new FruitOperation(FruitOperation.Operation.BALANCE,
                "apple", 20);
        fruitOperationDao.update(updatedApple);

        Optional<FruitOperation> result = fruitOperationDao.get("apple");
        assertTrue(result.isPresent());
        assertEquals(20, result.get().getQuantity());
    }

    @Test
    void testUpdateNonExistingFruit() {
        FruitOperation banana = new FruitOperation(FruitOperation.Operation.BALANCE, "banana", 15);
        fruitOperationDao.update(banana);

        Optional<FruitOperation> result = fruitOperationDao.get("banana");
        assertTrue(result.isPresent());
        assertEquals(15, result.get().getQuantity());
    }
}
