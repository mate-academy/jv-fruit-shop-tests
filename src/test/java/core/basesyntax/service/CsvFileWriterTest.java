package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileWriterTest {
    private static final String REPORT_FILE_PATH = "src/test/resources/reportTest.csv";
    private static final String WRONG_FILE_PATH = "src/test/resources/invalid/path.csv";
    private static final String REPORT_TEXT = "banana,100";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriter();
    }

    @Test
    public void write_IncorrectPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.write(WRONG_FILE_PATH, REPORT_TEXT));
    }

    @Test
    public void write_CorrectPath_Ok() throws IOException {
        fileWriter.write(REPORT_FILE_PATH, REPORT_TEXT);

        try (var reader = Files.newBufferedReader(Paths.get(REPORT_FILE_PATH),
                StandardCharsets.UTF_8)) {
            String actualContent = reader.readLine();
            assertEquals(REPORT_TEXT, actualContent);
        }
    }
}
