package myfirstproject.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import myfirstproject.model.Fruit;
import myfirstproject.service.PreparingData;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrepareDataImplTest {
    private static PreparingData preparingData;

    @BeforeClass
    public static void setUp() {
        preparingData = new PrepareDataImpl();
    }

    @Test
    public void isEmptyData_ok() {
        preparingData = new PrepareDataImpl();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = preparingData.prepare(Collections.emptyMap());
        assertEquals(expected, actual);
    }

    @Test
    public void isValidPreparedData_ok() {
        preparingData = new PrepareDataImpl();
        Fruit fruit = new Fruit("apple");
        Integer value = 10;
        Map<Fruit, Integer> map = new HashMap<>();
        map.put(fruit, value);
        String actual = preparingData.prepare(map);
        String expected = "fruit,quantity" + System.lineSeparator() + fruit.getName()
                + "," + value + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
