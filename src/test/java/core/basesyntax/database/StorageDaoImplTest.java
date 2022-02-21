package core.basesyntax.database;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDaoImpl storageDao;
    private static List<Fruit> fruitList;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storageDao = new StorageDaoImpl();
        fruitList = new ArrayList<>();
    }

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit("apple");
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void addFruit_ok() {
        storageDao.add(fruit);
        boolean actual = Storage.fruits.isEmpty();
        Assert.assertFalse("Fruit hasn't added to storage", actual);
    }

    @Test(expected = RuntimeException.class)
    public void addExistingFruit_notOk() {
        storageDao.add(fruit);
        storageDao.add(fruit);
    }

    @Test
    public void getFruit_ok() {
        Storage.fruits.add(fruit);
        Fruit actual = storageDao.get(fruit.getFruitName());
        Assert.assertEquals(fruit, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void getNotExistingFruit_notOk() {
        storageDao.get(fruit.getFruitName());
    }

    @Test
    public void fruitIsInStorage_ok() {
        storageDao.add(fruit);
        Assert.assertTrue(storageDao.fruitIsInStorage(fruit.getFruitName()));
    }

    @Test
    public void fruitIsInStorage_notOk() {
        Assert.assertFalse("This type of fruit should not be in storage",
                storageDao.fruitIsInStorage(fruit.getFruitName()));
    }

    @Test
    public void fruitGetAll_ok() {
        fruitList.add(new Fruit("banana"));
        fruitList.add(new Fruit("apple"));
        fruitList.add(new Fruit("lemon"));
        Storage.fruits.addAll(fruitList);
        Assert.assertEquals(fruitList, storageDao.getAll());
    }

}
