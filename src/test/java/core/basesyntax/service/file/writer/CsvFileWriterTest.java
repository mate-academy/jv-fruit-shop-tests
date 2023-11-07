package core.basesyntax.service.file.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String EXPECTED_REPORT = """
            fruit,quantity
            banana,152
            apple,90
            """;
    private static final String REPORT_FILEPATH = "test.csv";
    private static final String INVALID_REPORT_FILE_PATH = "invalid_path/report.csv";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriter();
    }

    @Test
    void writeIntoFile() {
        fileWriter.writeIntoFile(REPORT_FILEPATH, EXPECTED_REPORT);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(REPORT_FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(EXPECTED_REPORT, builder.toString());
    }

    @Test
    void writeIntoFile_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeIntoFile(INVALID_REPORT_FILE_PATH,EXPECTED_REPORT));
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(REPORT_FILEPATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
