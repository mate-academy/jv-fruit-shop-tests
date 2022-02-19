package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Test;

public class FruitDaoImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final Map<Fruit, Integer> fruitsStorage = Storage.fruitsStorage;
    private Fruit appleFruit;

    @Test
    public void setFruit_validFruit_ok() {
        appleFruit = new Fruit("apple");
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
    public void setQuantity_validData_ok() {
        appleFruit = new Fruit("apple");
        fruitDao.setQuantity(appleFruit, 100);
        Map<Fruit, Integer> fruitStorageExpected = Map.of(appleFruit, 100);
        assertEquals(fruitStorageExpected, fruitsStorage);
    }

    @Test
    public void setQuantity_toAlready_existedFruit_ok() {
        appleFruit = new Fruit("apple");
        fruitDao.addFruit(appleFruit);
        fruitDao.setQuantity(appleFruit, 100);
        Map<Fruit, Integer> fruitStorageExpected = Map.of(appleFruit, 100);
        assertEquals(fruitStorageExpected, fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void setQuantity_quantityLowerZero_notOk() {
        appleFruit = new Fruit("apple");
        fruitDao.setQuantity(appleFruit, -100);
    }

    @Test(expected = RuntimeException.class)
    public void setQuantity_nullFruitType_notOk() {
        appleFruit = new Fruit(null);
        fruitDao.setQuantity(appleFruit, 100);
    }

    @Test(expected = RuntimeException.class)
    public void setQuantity_nullFruit_notOk() {
        appleFruit = null;
        fruitDao.setQuantity(appleFruit, 100);
    }

    @Test
    public void getQuantity_validFruit_ok() {
        appleFruit = new Fruit("apple");
        fruitsStorage.put(appleFruit, 100);
        Integer expectedQuantity = 100;
        assertEquals(expectedQuantity, fruitDao.getQuantity(appleFruit));
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_noSuchFruit_notOk() {
        appleFruit = new Fruit("apple");
        fruitsStorage.put(appleFruit, 100);
        fruitDao.getQuantity(new Fruit("banan"));
    }

    @Test
    public void getFruitsSet_validData_ok() {
        appleFruit = new Fruit("apple");
        Fruit banan = new Fruit("banan");
        fruitsStorage.put(appleFruit, 100);
        fruitsStorage.put(banan, 200);
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
