package core.basesyntax.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String REPORT_FILE_PATH = "src/test/resources/report.csv";
    private static final String WRONG_DIR_FILE_PATH = "src/test/resources/wrong/report.csv";
    private static FileWriter fileWriter;
    private final String dataToWrite = "Some data to test";

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_newReport_ok() {
        Assertions.assertDoesNotThrow(() -> fileWriter.write(dataToWrite, REPORT_FILE_PATH));
    }

    @Test
    void write_wrongDir_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileWriter
                .write(dataToWrite, WRONG_DIR_FILE_PATH));
    }

    @Test
    void write_verifyWrittenContent_ok() {
        List<String> actual;
        List<String> expected = new ArrayList<>();
        expected.add("Some data to test");
        fileWriter.write(dataToWrite, REPORT_FILE_PATH);
        try {
            actual = Files.readAllLines(Paths.get(REPORT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the file: " + REPORT_FILE_PATH, e);
        }
        Assertions.assertEquals(expected, actual);
    }
}
