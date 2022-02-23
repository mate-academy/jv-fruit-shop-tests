package core.fruitshop.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import core.fruitshop.db.Storage;
import core.fruitshop.model.Fruit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Test;

public class FruitDaoImplTest {
    public static final int QUANTITY = 100;
    public static final String APPLE = "apple";
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final Map<Fruit, Integer> fruitsStorage = Storage.fruitsStorage;
    private Fruit appleFruit;

    @Test
    public void setFruit_validFruit_ok() {
        appleFruit = new Fruit(APPLE);
        fruitDao.addFruit(appleFruit);
        assertTrue(fruitsStorage.containsKey(appleFruit));
    }

    @Test(expected = RuntimeException.class)
    public void setFruit_null_notOk() {
        appleFruit = null;
        fruitDao.addFruit(appleFruit);
    }

    @Test(expected = RuntimeException.class)
    public void setFruit_nullFruit_notOk() {
        appleFruit = new Fruit(null);
        fruitDao.addFruit(appleFruit);
    }

    @Test
    public void getQuantity_validFruit_ok() {
        appleFruit = new Fruit(APPLE);
        fruitsStorage.put(appleFruit, QUANTITY);
        Integer expectedQuantity = QUANTITY;
        assertEquals(expectedQuantity, fruitDao.getQuantity(appleFruit));
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_noSuchFruit_notOk() {
        appleFruit = new Fruit(APPLE);
        fruitsStorage.put(appleFruit, QUANTITY);
        fruitDao.getQuantity(new Fruit("banana"));
    }

    @Test
    public void getFruitsSet_validData_ok() {
        appleFruit = new Fruit(APPLE);
        Fruit banan = new Fruit("banana");
        fruitsStorage.put(appleFruit, QUANTITY);
        fruitsStorage.put(banan, QUANTITY);
        Set<Fruit> expectedFruitSet = Set.of(appleFruit, banan);
        assertEquals(expectedFruitSet, fruitDao.getFruitsSet());
    }

    @Test
    public void getFruitsSet_emptyStorage_ok() {
        Set<Fruit> expectedFruitSet = new HashSet<>();
        assertEquals(expectedFruitSet, fruitDao.getFruitsSet());
    }

    @After
    public void tearDown() {
        fruitsStorage.clear();
    }
}
