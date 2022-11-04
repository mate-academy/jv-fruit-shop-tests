package myfirstproject.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import myfirstproject.model.Fruit;
import myfirstproject.service.PreparingData;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrepareDataImplTest {
    private static final String TITLE = "fruit,quantity" + System.lineSeparator();
    private static String expected;
    private static String actual;

    @BeforeClass
    public static void before() {
        PreparingData preparingData = new PrepareDataImpl();
        Fruit fruit = new Fruit("apple");
        Integer value = 10;
        Map<Fruit, Integer> map = new HashMap<>();
        map.put(fruit, value);
        StringBuilder builder = new StringBuilder();
        actual = preparingData.prepare(builder, map);
        expected = TITLE + fruit.getName() + "," + value + System.lineSeparator();
    }

    @Test
    public void isValidPreparedData_Ok() {
        assertEquals(expected, actual);
    }
}
