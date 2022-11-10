package myfirstproject.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import myfirstproject.model.Fruit;
import myfirstproject.service.PreparingData;
import org.junit.Test;


public class PrepareDataImplTest {
    @Test
    public void isEmptyData_Ok() {
        PreparingData preparingData = new PrepareDataImpl();
        String expected = preparingData.prepare(Collections.emptyMap());
        String actual = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void isValidPreparedData_Ok() {
        PreparingData preparingData = new PrepareDataImpl();
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
