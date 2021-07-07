package service;

import models.Fruit;
import org.junit.Test;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import static org.junit.Assert.*;

public class DataProcessorFruitsImplTest {
    private static DataProcessor<Map<Fruit, Integer>, String> processor = new DataProcessorFruitsImpl();
    private static Map<Fruit, Integer> mapOfFruits = Map.of(new Fruit("banana"), 100,
            new Fruit("tangerine"), 200,
            new Fruit("apple"), 400);

    @Test
    public void processWithMap_Ok() {
        String result = processor.process(mapOfFruits);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Fruit, Integer> entry : mapOfFruits.entrySet()) {
            builder.append(entry.getKey().getName())
                    .append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        String expected = String.join(System.lineSeparator(), builder.toString());
        assertEquals(expected, result);
    }

    @Test
    public void processEmptyMap_Ok() {
        String result = processor.process(Collections.emptyMap());
        assertEquals("", result);
    }
}