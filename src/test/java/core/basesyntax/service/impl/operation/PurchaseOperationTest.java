package core.basesyntax.service.impl.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static Storage storage;
    private static FruitStorageDaoImpl fruitStorageDao;
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage(new HashMap<>());
        fruitStorageDao = new FruitStorageDaoImpl(storage);
        purchaseOperation = new PurchaseOperation(fruitStorageDao);
    }

    @BeforeEach
    void setUp() {
        storage.getFruits().put(new Fruit("apple"), 10);
        storage.getFruits().put(new Fruit("peach"), 50);
    }

    @AfterEach
    void tearDown() {
        storage.getFruits().clear();
    }

    @Test
    void applyOperation_validParameters_ok() {
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("apple"), 0,
                new Fruit("peach"), 20
        );
        assertTrue(purchaseOperation.applyOperation(new Fruit("apple"), 10));
        assertTrue(purchaseOperation.applyOperation(new Fruit("peach"), 30));
        Map<Fruit, Integer> actual = storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    void applyOperation_nullFruitParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.applyOperation(null, 10));
    }

    @Test
    void applyOperation_negativeQuantityParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.applyOperation(new Fruit("apple"), -1));
    }

    @Test
    void applyOperation_balanceLessThanQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.applyOperation(new Fruit("apple"), 11));
    }

    @Test
    void applyOperation_notExistFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.applyOperation(new Fruit("banana"), 10));
    }
}
