package core.basesyntax.db;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class FruitShopStorageTest {
    private static final StorageDaoImpl daoTest = new StorageDaoImpl();
    private static final Fruit apple = new Fruit("apple", 35);
    private static final Fruit orange = new Fruit("orange", 41);
    private static final Fruit peach = new Fruit("peach", 20);
    private static final Fruit apricot = new Fruit("apricot", 27);

    @AfterClass
    public static void tearDown() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void get_allFruits_Ok() {
        int expectedSize = 4;
        daoTest.addFruit(apple);
        daoTest.addFruit(orange);
        daoTest.addFruit(peach);
        daoTest.addFruit(apricot);
        int size = FruitShopStorage.getStorageFruits().size();
        boolean actual = size == expectedSize;
        Assert.assertTrue(actual);
    }
}
