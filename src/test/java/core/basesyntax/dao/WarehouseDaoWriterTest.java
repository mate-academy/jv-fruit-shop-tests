package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WarehouseDaoWriterTest {
    public static final String PATH_TEST_FILE = "src/test/resources/output.csv";
    private static final WarehouseDaoWriter warehouseDaoWriter = new WarehouseDaoWriter();

    @Test
    void writeData_normalBehavior_ok() {
        Map<String, Integer> fruitMap = new HashMap<>();
        fruitMap.put("banana", 20);
        fruitMap.put("apple", 42);
        warehouseDaoWriter.writeData(PATH_TEST_FILE, fruitMap);
        String actualReport = "";
        try {
            actualReport = String.join(
                    System.lineSeparator(), Files.readAllLines(Path.of(PATH_TEST_FILE)));
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,42";
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void writeToFile_nullData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> warehouseDaoWriter.writeData(PATH_TEST_FILE, null));
    }

    @Test
    void writeToFile_nullPath_notOk() {
        Map<String, Integer> fruitMap = new HashMap<>();
        fruitMap.put("banana", 20);
        fruitMap.put("apple", 42);
        Assertions.assertThrows(RuntimeException.class,
                () -> warehouseDaoWriter.writeData(null, fruitMap));
    }
}
