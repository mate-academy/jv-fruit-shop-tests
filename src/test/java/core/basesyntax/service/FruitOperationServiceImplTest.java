package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitOperationDao;
import core.basesyntax.model.FruitOperation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitOperationServiceImplTest {
    private InMemoryFruitOperationDao fruitOperationDao;
    private FruitOperationServiceImpl fruitOperationService;

    @BeforeEach
    void setUp() {
        fruitOperationDao = new InMemoryFruitOperationDao();
        fruitOperationService = new FruitOperationServiceImpl(fruitOperationDao);
    }

    @Test
    void testCreateNewFruitOperation() {
        String fruitName = "apple";

        fruitOperationService.createNewFruitOperation(fruitName);

        Optional<FruitOperation> stored = fruitOperationDao.get(fruitName);
        assertTrue(stored.isPresent(), "FruitOperation should be present in DAO");
        assertEquals(fruitName, stored.get().getFruit(), "Fruit name should match");
    }

    @Test
    void testCreateNewFruitOperationMultipleFruits() {
        fruitOperationService.createNewFruitOperation("banana");
        fruitOperationService.createNewFruitOperation("kiwi");

        assertTrue(fruitOperationDao.get("banana").isPresent(), "Banana should be stored");
        assertTrue(fruitOperationDao.get("kiwi").isPresent(), "Kiwi should be stored");
    }

    @Test
    void testCreateNewFruitOperationOverridesExisting() {
        // First create
        fruitOperationService.createNewFruitOperation("mango");
        // Add again (should overwrite in simple DAO)
        fruitOperationService.createNewFruitOperation("mango");

        Optional<FruitOperation> stored = fruitOperationDao.get("mango");
        assertTrue(stored.isPresent(), "Mango should be present");
        assertEquals("mango", stored.get().getFruit());
    }

    // ----------- In-memory DAO stub -----------

    static class InMemoryFruitOperationDao implements FruitOperationDao {
        private final Map<String, FruitOperation> store = new HashMap<>();

        @Override
        public void add(FruitOperation fruit) {
            store.put(fruit.getFruit(), fruit);
        }

        @Override
        public Optional<FruitOperation> get(String fruit) {
            return Optional.ofNullable(store.get(fruit));
        }

        @Override
        public void update(FruitOperation fruit) {
            store.put(fruit.getFruit(), fruit);
        }
    }
}
