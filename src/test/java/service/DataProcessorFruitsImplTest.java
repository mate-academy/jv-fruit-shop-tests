package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.Map;
import models.Fruit;
import org.junit.Test;

public class DataProcessorFruitsImplTest {
    private static DataProcessor<Map<Fruit, Integer>, String> processor
            = new DataProcessorFruitsImpl();
    private static Map<Fruit, Integer> mapOfFruits = Map.of(
            new Fruit("banana"), 100,
            new Fruit("tangerine"), 200,
            new Fruit("apple"), 400);

    @Test
    public void processWithMap_Ok() {
        String result = processor.process(mapOfFruits);
        String[] expected = new StringBuilder()
            .append("banana").append(",")
                .append(100).append(" ")
                .append("tangerine").append(",")
                .append(200).append(" ")
                .append("apple").append(",")
                .append(400).append(" ")
                .toString().split(" ");
        for (String string : expected) {
            if (!(result.contains(string))) {
                fail();
            }
        }
    }

    @Test
    public void processEmptyMap_Ok() {
        String result = processor.process(Collections.emptyMap());
        assertEquals("", result);
    }
}
