package core.basesyntax;

import core.basesyntax.db.FileWriter;
import core.basesyntax.db.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        String actual;
        try {
            actual = Files.readAllLines(Paths.get(REPORT_FILE_PATH)).get(0);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the file: " + REPORT_FILE_PATH, e);
        }
        Assertions.assertEquals(dataToWrite, actual);
    }
}
