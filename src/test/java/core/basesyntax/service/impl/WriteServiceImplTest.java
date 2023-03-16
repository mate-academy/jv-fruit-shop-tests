package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class WriteServiceImplTest {
    private WriteService writeService;

    @Before
    public void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test
    public void writeAndRead_toFile_Ok() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("banana", 250);
        testMap.put("apple", 61);
        writeService.writeToFile(testMap, "src/test/java/resources/testToFile.csv");

        List<String> expected = List.of("fruit,quantity",
                "banana,250",
                "apple,61");
        ReadServiceImpl readService = new ReadServiceImpl();
        assertEquals(expected, readService.readFromFile("src/test/java/resources/testToFile.csv"));
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPath_notOk() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("orange", 55);
        testMap.put("apple", 42);

        writeService.writeToFile(testMap, "src/test/resources/testToFile.csv");
    }
}
