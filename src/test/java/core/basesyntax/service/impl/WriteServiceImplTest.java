package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class WriteServiceImplTest {

    @Test
    public void write_toFile_Ok() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("banana", 250);
        testMap.put("apple", 61);

        WriteService writeService = new WriteServiceImpl();
        writeService.writeToFile(testMap, "src/test/testToFile.csv");

        List<String> expected = List.of("fruit,quantity",
                "banana,250",
                "apple,61");
        ReadServiceImpl readService = new ReadServiceImpl();
        assertEquals(expected, readService.readFromFile("src/test/testToFile.csv"));
    }
}
