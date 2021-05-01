package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final int QUANTITY = 100;
    private static final int QUANTITY_FOR_UPDATE = 10;
    private static final String APPLE = "apple";
    private static FruitDao fruitDao;
    private static final Fruit FRUIT = new Fruit(APPLE, QUANTITY);
    private static final Fruit FRUIT_WITH_NEW_QUANTITY = new Fruit(APPLE,
            QUANTITY + QUANTITY_FOR_UPDATE);

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void addTest_Ok() {
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(FRUIT);
        fruitDao.add(FRUIT);
        assertEquals(fruitList, Storage.fruitStorage);
    }

    @Test
    public void updateTest_Ok() {
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(FRUIT_WITH_NEW_QUANTITY);
        fruitDao.add(FRUIT);
        fruitDao.update(FRUIT.getName(), QUANTITY_FOR_UPDATE);
        assertEquals(fruitList, Storage.fruitStorage);
    }

    @Test
    public void getBalanceTest_Ok() {
        assertEquals(QUANTITY, FRUIT.getBalance());
    }

    @Test
    public void getAllTest_Ok() {
        List<Fruit> expected = new ArrayList<>();
        List<Fruit> actual = fruitDao.getAll();
        assertEquals(expected, actual);
    }

    @After
    public void free() {
        Storage.fruitStorage.clear();
    }
}
