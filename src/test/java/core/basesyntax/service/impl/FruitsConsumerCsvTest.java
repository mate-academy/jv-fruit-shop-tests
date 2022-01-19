package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitsConsumer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitsConsumerCsvTest {
    private static final String CSV_HEADER = "fruit,quantity";
    private static final String FRUIT_REPORT_PATH = "src/test/resources/fruits.csv";
    private static final FruitsConsumer fruitsConsumer = new FruitsConsumerCsv(FRUIT_REPORT_PATH);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(FRUIT_REPORT_PATH));
    }

    @Test
    public void accept_FruitMap_Ok() {
        Map<Fruit, Integer> expected = getFruitMap_Ok();
        fruitsConsumer.accept(expected);
        List<String> csvRows = loadLinesFromFile(FRUIT_REPORT_PATH);
        assertEquals("Report header", CSV_HEADER, csvRows.get(0));
        Map<Fruit, Integer> actual = getFruitMapFromLines(csvRows);
        assertEquals("Report records count", expected.size(), actual.size());
        for (Map.Entry<Fruit, Integer> entry : expected.entrySet()) {
            assertTrue("report file doesn't contain '" + entry.getKey() + "'",
                    actual.containsKey(entry.getKey()));
            assertEquals("Amount of " + entry.getKey() + " in report",
                    entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    public void accept_EmptyMap_Ok() {
        Map<Fruit, Integer> emptyMap = new HashMap<>();
        fruitsConsumer.accept(emptyMap);
        List<String> actual = loadLinesFromFile(FRUIT_REPORT_PATH);
        assertEquals("Report rows", 1, actual.size());
        assertEquals("Report header", CSV_HEADER, actual.get(0));
    }

    @Test(expected = RuntimeException.class)
    public void accept_Null_NotOk() {
        fruitsConsumer.accept(null);
    }

    private Map<Fruit, Integer> getFruitMap_Ok() {
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("apple"), 30);
        expectedMap.put(new Fruit("banana"), 1000);
        expectedMap.put(new Fruit("fruit max"), Integer.MAX_VALUE);
        expectedMap.put(new Fruit("fruit min"), Integer.MIN_VALUE);
        return expectedMap;
    }

    private Map<Fruit, Integer> getFruitMapFromLines(List<String> csvRows) {
        return csvRows.stream()
                .skip(1)
                .map(s -> s.split(FileUtils.COMMA_SEPARATOR))
                .collect(Collectors.toMap(arr -> new Fruit(arr[0]),
                        arr -> Integer.parseInt(arr[1])));
    }

    private List<String> loadLinesFromFile(String filePath) {
        try (Stream<String> linesStream = Files.lines(Path.of(filePath))) {
            return linesStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: '" + filePath + "'", e);
        }
    }
}
