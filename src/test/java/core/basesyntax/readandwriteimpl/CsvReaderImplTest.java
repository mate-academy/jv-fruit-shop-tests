package core.basesyntax.readandwriteimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvReaderImplTest {
    @Test
    public void testReadDataFromFile() {
        String testFilePath = "test.csv";
        createTestFile(testFilePath);

        CsvReaderImpl csvReader = new CsvReaderImpl();

        List<String> lines = csvReader.readDataFromFile("src/main/java/core/basesyntax"
                + "/resources/input.csv");

        assertEquals(3, lines.size());
        assertTrue(lines.contains("1,John,Doe"));
        assertTrue(lines.contains("2,Jane,Smith"));
        assertTrue(lines.contains("3,Bob,Johnson"));

        deleteTestFile(testFilePath);
    }

    private void createTestFile(String filePath) {
        List<String> data = Arrays.asList("1,John,Doe", "2,Jane,Smith", "3,Bob,Johnson");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteTestFile(String filePath) {
        try {
            Files.deleteIfExists(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
