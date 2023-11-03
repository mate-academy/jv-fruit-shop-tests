package core.basesyntax.service.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String REPORT = """
            fruit,quantity
            banana,152
            apple,90
            """;
    private static final String TEST_FILE_PATH = "test.csv";
    private CsvFileWriter csvFileWriter;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        csvFileWriter = new CsvFileWriter();
        testFilePath = TEST_FILE_PATH;
    }

    @Test
    void testWriteToFile_withValidPath_ok() {
        csvFileWriter.writeToFile(REPORT, testFilePath);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(REPORT, builder.toString());
    }

    @Test
    void testWriteToFile_withInvalidPath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            csvFileWriter.writeToFile("Test", "nonexistent/test.csv");
        });
    }
}
