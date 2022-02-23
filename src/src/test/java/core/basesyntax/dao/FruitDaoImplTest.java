package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Test;

public class FruitDaoImplTest {
    private FruitDao fruitDao = new FruitDaoImpl();
    private List<String> testList;

    @Test(expected = RuntimeException.class)
    public void putNullList_notOk() {
        testList = null;
        fruitDao.putDataIntoStorage(testList);
    }

    @Test
    public void putList_Ok() {
        testList = List.of("b,orange,20", "b,banana,70",
                "b,apple,100", "s,orange,50", "p,orange,20",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50", "s,banana,43");
        fruitDao.putDataIntoStorage(testList);
        Integer expectedBananaQuantity = 245;
        Integer expectedAppleQuantity = 90;
        Integer expectedOrangeQuantity = 50;
        Integer actualBananaQuantity = Storage.FRUIT_STORAGE.get(new Fruit("banana"));
        Integer actualAppleQuantity = Storage.FRUIT_STORAGE.get(new Fruit("apple"));
        Integer actualOrangeQuantity = Storage.FRUIT_STORAGE.get(new Fruit("orange"));
        assertEquals(expectedAppleQuantity, actualAppleQuantity);
        assertEquals(expectedBananaQuantity, actualBananaQuantity);
        assertEquals(expectedOrangeQuantity, actualOrangeQuantity);
        Storage.FRUIT_STORAGE.clear();
    }

    @Test
    public void getDataFromNonEmptyStorage_Ok() {
        Storage.FRUIT_STORAGE.put(new Fruit("lemon"), 25);
        Storage.FRUIT_STORAGE.put(new Fruit("banana"), 3);
        Storage.FRUIT_STORAGE.put(new Fruit("pineapple"), 53);
        String actual = fruitDao.getDataFromStorage().toString();
        String expected = "[lemon=25, banana=3, pineapple=53]";
        assertEquals(expected, actual);
        Storage.FRUIT_STORAGE.clear();
    }

    @Test
    public void getDataFromEmptyStorage_Ok() {
        String expected = "[]";
        String actual = fruitDao.getDataFromStorage().toString();
        assertEquals(expected, actual);
    }
}
