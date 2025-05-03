package core.basesyntax.service.impl.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static Storage storage;
    private static FruitStorageDaoImpl fruitStorageDao;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage(new HashMap<>());
        fruitStorageDao = new FruitStorageDaoImpl(storage);
        balanceOperation = new BalanceOperation(fruitStorageDao);
    }

    @BeforeEach
    void setUp() {
        storage.getFruits().clear();
    }

    @Test
    void applyOperation_validParameters_ok() {
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("apple"), 1,
                new Fruit("peach"), 50
        );
        assertTrue(balanceOperation.applyOperation(new Fruit("apple"), 1));
        assertTrue(balanceOperation.applyOperation(new Fruit("peach"), 50));
        Map<Fruit, Integer> actual = storage.getFruits();
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
