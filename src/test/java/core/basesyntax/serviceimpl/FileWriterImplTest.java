package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String VALID_REPORT_FILE_PATH = "src/test/resources"
            + "/finalReportValidDataTest.csv";
    private static final String EMPTY_FILE_PATH = "";
    private static final String EMPTY_DATA = "";
    private static final String TEST_VALID_REPORT = "fruit, quantity" + System.lineSeparator()
            + "banana, 140" + System.lineSeparator();

    @Test
    void write_ValidData_Ok() {
        FileWriter fileWriter = new FileWriterImpl();

        fileWriter.write(TEST_VALID_REPORT, VALID_REPORT_FILE_PATH);

        assertTrue(Files.exists(Paths.get(VALID_REPORT_FILE_PATH)));

        try {
            Files.delete(Paths.get(VALID_REPORT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Error write data to test file "
                    + VALID_REPORT_FILE_PATH, e);
        }
    }

    @Test
    void write_EmptyFilePath_NotOk() {
        FileWriter fileWriter = new FileWriterImpl();

        assertThrows(RuntimeException.class,
                () -> fileWriter.write(TEST_VALID_REPORT, EMPTY_FILE_PATH));
    }

    @Test
    void write_EmptyData_NotOk() {
        FileWriter fileWriter = new FileWriterImpl();

        assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_DATA, VALID_REPORT_FILE_PATH));
    }
}
