package core.basesyntax.service.impl.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.TestFruitStorageDaoImpl;
import core.basesyntax.db.TestStorage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static TestFruitStorageDaoImpl fruitStorageDao;
    private static ReturnOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new TestFruitStorageDaoImpl();
        returnOperation = new ReturnOperation(fruitStorageDao);
    }

    @BeforeEach
    void setUp() {
        TestStorage.fruits.put(new Fruit("apple"), 0);
        TestStorage.fruits.put(new Fruit("peach"), 0);
    }

    @AfterEach
    void tearDown() {
        TestStorage.fruits.clear();
    }

    @Test
    void applyOperation_validParameters_ok() {
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("apple"), 10,
                new Fruit("peach"), 5
        );
        assertTrue(returnOperation.applyOperation(new Fruit("apple"), 10));
        assertTrue(returnOperation.applyOperation(new Fruit("peach"), 5));
        Map<Fruit, Integer> actual = TestStorage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    void applyOperation_nullFruitParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> returnOperation.applyOperation(null, 10));
    }

    @Test
    void applyOperation_negativeQuantityParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> returnOperation.applyOperation(new Fruit("apple"), -1));
    }

    @Test
    void applyOperation_notExistFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> returnOperation.applyOperation(new Fruit("banana"), 10));
    }
}
