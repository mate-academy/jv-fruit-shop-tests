package core.basesyntax.readandwriteimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvReaderImplTest {
    @Test
    public void testReadDataFromFile() {
        String testFilePath = "test.csv";
        createTestFile(testFilePath);

        CsvReaderImpl csvReader = new CsvReaderImpl();

        List<String> lines = csvReader.readDataFromFile("src/main/resources/input.csv");

        assertEquals(3, lines.size());
        assertTrue(lines.contains("1,John,Doe"));
        assertTrue(lines.contains("2,Jane,Smith"));
        assertTrue(lines.contains("3,Bob,Johnson"));

        deleteTestFile(testFilePath);
    }

    private void createTestFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("1,John,Doe\n");
            writer.write("2,Jane,Smith\n");
            writer.write("3,Bob,Johnson\n");
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
