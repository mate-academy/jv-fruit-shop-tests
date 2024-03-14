package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static final String TEST_FRUIT_NAME = "apple";
    private static final int TEST_FRUIT_QUANTITY = 10;
    private FruitDao fruitDao;
    private Fruit apple;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        apple = new Fruit(TEST_FRUIT_NAME,TEST_FRUIT_QUANTITY);
        Storage.getInstance().getStorage().clear();
        fruitDao.add(apple);
    }

    @Test
    void addFruit_notNull_success() {
        assertNotNull(fruitDao.get(TEST_FRUIT_NAME));
    }

    @Test
    void getFruit_existingName_success() {
        assertEquals(apple, fruitDao.get(TEST_FRUIT_NAME));
    }

    @Test
    void updateFruit_validFruit_success() {
        Fruit updatedApple = new Fruit(TEST_FRUIT_NAME, TEST_FRUIT_QUANTITY);
        fruitDao.update(updatedApple);
        assertEquals(updatedApple, fruitDao.get(TEST_FRUIT_NAME));
    }

    @Test
    void getAllFruits_sorted_success() {
        String bananaFruit = "banana";
        int bananaQuantity = 20;
        Fruit banana = new Fruit(bananaFruit,bananaQuantity);
        fruitDao.add(banana);
        List<Fruit> fruits = fruitDao.getAll();
        assertEquals(TEST_FRUIT_NAME, fruits.get(0).getFruitName());
        assertEquals(bananaFruit, fruits.get(1).getFruitName());
    }

    @AfterEach
    void tearDown() {
        Storage.getInstance().getStorage().clear();
    }
}
