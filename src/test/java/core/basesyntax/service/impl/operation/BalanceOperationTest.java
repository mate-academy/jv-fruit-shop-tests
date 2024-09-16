package core.basesyntax.service.impl.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.TestFruitStorageDaoImpl;
import core.basesyntax.db.TestStorage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static TestFruitStorageDaoImpl fruitStorageDao;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new TestFruitStorageDaoImpl();
        balanceOperation = new BalanceOperation(fruitStorageDao);
    }

    @BeforeEach
    void setUp() {
        TestStorage.fruits.clear();
    }

    @Test
    void applyOperation_validParameters_ok() {
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("apple"), 1,
                new Fruit("peach"), 50
        );
        assertTrue(balanceOperation.applyOperation(new Fruit("apple"), 1));
        assertTrue(balanceOperation.applyOperation(new Fruit("peach"), 50));
        Map<Fruit, Integer> actual = TestStorage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    void applyOperation_nullFruitParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> balanceOperation.applyOperation(null, 10));
    }

    @Test
    void applyOperation_negativeQuantityParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> balanceOperation.applyOperation(new Fruit("apple"), -1));
    }
}
