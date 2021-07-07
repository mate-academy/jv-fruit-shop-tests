package service;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String SYMBOL_FOR_SEPARATING = ",";
    private static Storage storage;

    @BeforeClass
    public static void operationBeforeTest() {
        storage = new Storage();
    }

    @After
    public void operationAfterTest() {
        Storage.storage.clear();
    }

    @Test
    public void getReport_validData_Ok() {
        Fruit peach = new Fruit("peach");
        Storage.storage.put(peach, 25);
        FruitService fruitService = new FruitServiceImpl();
        String actual = fruitService.getReport();
        StringBuilder stringBuilder = new StringBuilder();
        String expected = stringBuilder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append(peach.getName())
                .append(SYMBOL_FOR_SEPARATING)
                .append(25)
                .append(System.lineSeparator())
                .toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_EmptyMap_Ok() {
        FruitService fruitService = new FruitServiceImpl();
        String actual = fruitService.getReport();
        StringBuilder stringBuilder = new StringBuilder();
        String expected = stringBuilder.append("fruit,quantity")
                .append(System.lineSeparator()).toString();
        assertEquals(expected, actual);
    }
}
