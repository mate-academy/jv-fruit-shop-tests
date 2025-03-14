package core.basesyntax;

import core.basesyntax.db.FileWriter;
import core.basesyntax.db.FileWriterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String REPORT_FILE_PATH = "src/main/resources/report.csv";
    private static final String WRONG_DIR_FILE_PATH = "src/main/resources/wrong/report.csv";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_newReport_ok() {
        String data = "Some data to test";
        Assertions.assertDoesNotThrow(() -> fileWriter.write(data, REPORT_FILE_PATH));
    }

    @Test
    void write_wrongDir_notOk() {
        String data = "Some data to test";
        Assertions.assertThrows(RuntimeException.class, () -> fileWriter
                .write(data, WRONG_DIR_FILE_PATH));
    }
}
