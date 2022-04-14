package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Optional;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopDaoMapImplTest {
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String BANANA = "banana";
    private static FruitShopDao fruitShopDao;

    @BeforeClass
    public static void setFruitShopDao() {
        fruitShopDao = new FruitShopDaoMapImpl();
    }

    @Test
    public void add_storageWithData_isOK() {
        Fruit apple = new Fruit("apple", 100);
        fruitShopDao.add(apple);
        Optional<Fruit> fruitOptional = fruitShopDao.get(apple.getName());
        assertEquals(apple, fruitOptional.get());
    }

    @Test
    public void get_storageWithNoData_isEmpty() {
        Optional<Fruit> fruitOptional = fruitShopDao.get(BANANA);
        assertEquals(Optional.empty(), fruitOptional);
    }

    @Test
    public void getAll_storageWithData_isOK() {
        Fruit apple = new Fruit(APPLE, 100);
        Fruit orange = new Fruit(ORANGE, 200);
        fruitShopDao.add(apple);
        fruitShopDao.add(orange);
        List<Fruit> fruitList = fruitShopDao.getAll();
        assertTrue(fruitList.contains(apple));
        assertTrue(fruitList.contains(orange));
    }
}
