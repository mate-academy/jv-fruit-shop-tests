package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitRepository;
import core.basesyntax.db.Database;
import core.basesyntax.exception.StorageException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitRepositoryImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static FruitRepository fruitRepository;
    private static final Map<String, Integer> db = Database.storage;

    @BeforeAll
    static void beforeAll() {
        fruitRepository = new FruitRepositoryImpl();
    }

    @AfterEach
    void tearDown() {
        db.clear();
    }

    @Test
    void add_addFruits_ok() {
        fruitRepository.add(BANANA, 20);
        assertEquals(Map.of(BANANA, 20), db);
    }

    @Test
    void remove_removeFruits_ok() {
        db.put(BANANA, 20);
        fruitRepository.remove(BANANA, 15);
        assertEquals(Map.of(BANANA, 5), db);
    }

    @Test
    void remove_notEnoughFruits_notOk() {
        db.put(BANANA, 10);
        StorageException exception = assertThrows(StorageException.class, () -> {
            fruitRepository.remove(BANANA, 20);
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Not enough banana in storage to remove";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void hasFruit_true_ok() {
        db.put(BANANA, 14);
        assertTrue(fruitRepository.hasFruit(BANANA));
    }

    @Test
    void hasFruit_false_ok() {
        assertFalse(fruitRepository.hasFruit(BANANA));
    }

    @Test
    void getAll_ok() {
        db.put(BANANA, 20);
        db.put(APPLE, 15);
        Map<String, Integer> expectedMap = Map.of(
                BANANA, 20,
                APPLE, 15
        );
        Map<String, Integer> actualMap = fruitRepository.getAll();
        assertEquals(expectedMap, actualMap);
    }
}
