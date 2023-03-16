package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class WriteServiceImplTest {
    private WriteService writeService;
    private ReadServiceImpl readService;
    private Map<String, Integer> testMap;

    @Before
    public void setUp() {
        writeService = new WriteServiceImpl();
        readService = new ReadServiceImpl();
        testMap = new HashMap<>();
    }

    @Test
    public void writeAndRead_toFile_Ok() {
        testMap.put("banana", 250);
        testMap.put("apple", 61);
        writeService.writeToFile(testMap, "src/test/java/resources/testToFile.csv");

        List<String> expected = List.of("fruit,quantity",
                "banana,250",
                "apple,61");
        assertEquals(expected, readService.readFromFile("src/test/java/resources/testToFile.csv"));
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPath_notOk() {
        testMap.put("orange", 55);
        testMap.put("apple", 42);

        writeService.writeToFile(testMap, "src/test/resources/testToFile.csv");
    }

    @Test
    public void write_emptyMap_Ok() {
        writeService.writeToFile(testMap, "src/test/java/resources/writeEmpty.csv");
        try {
            String result = Files.readString(Path.of("src/test/java/resources/writeEmpty.csv"));
            assertEquals(16, result.length());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
