package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    public static final String APPLE = "apple";
    public static final String BANANA = "apple";
    public static final int TEST_FRUIT_QUANTITY = 10;
    private FruitDao fruitDao = new FruitDaoImpl();
    private Fruit apple = new Fruit(APPLE, TEST_FRUIT_QUANTITY);

    @BeforeEach
    void setUp() {
        Storage.getInstance().getStorage().clear();
        fruitDao.add(apple);
    }

    @Test
    void addFruit_notNullAndValidAdded_success() {
        Fruit expected = apple;
        assertNotNull(fruitDao.get(APPLE));
        assertEquals(expected, fruitDao.get(APPLE));
    }

    @Test
    void getFruit_existingName_success() {
        assertEquals(apple, fruitDao.get(APPLE));
    }

    @Test
    void updateFruit_validFruit_success() {
        Fruit updatedApple = new Fruit(APPLE, TEST_FRUIT_QUANTITY);
        fruitDao.update(updatedApple);
        assertEquals(updatedApple, fruitDao.get(APPLE));
    }

    @Test
    void getAllFruits_sorted_success() {
        int quantity = 20;
        Fruit bananaFruit = new Fruit(BANANA, quantity);
        fruitDao.add(bananaFruit);
        List<Fruit> fruits = fruitDao.getAll();
        assertEquals(APPLE, fruits.get(0).getFruitName());
        assertEquals(BANANA, fruits.get(1).getFruitName());
    }
}
