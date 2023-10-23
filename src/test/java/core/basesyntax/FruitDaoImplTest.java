package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.basesyntax.model.Fruit;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDaoImplTest {
    private FruitDaoImpl fruitDao;

    @BeforeEach
    public void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void add() {
        assertNotNull(fruitDao);

        Fruit banana = new Fruit("banana", 50);
        fruitDao.add(banana);

        Optional<Fruit> retrievedFruit = fruitDao.getFruitIfPresent("banana");
        assertTrue(retrievedFruit.isPresent(), "Expected to find banana");
        assertEquals("banana", retrievedFruit.get().getName());
        assertEquals(50, retrievedFruit.get().getQuantity());
    }

    @Test
    public void getFruitIfPresent_NotPresent() {
        assertNotNull(fruitDao);

        Optional<Fruit> retrievedFruit = fruitDao.getFruitIfPresent("peach");
        assertFalse(retrievedFruit.isPresent(), "Expected not to find peach");
    }
}
