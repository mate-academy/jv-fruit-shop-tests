package core.basesyntax.service.fileservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String VALID_FILE_REPORT =
            "src/test/resources/validFinalReport.csv";
    private static final String INVALID_FILE_REPORT = "src/test/resources";

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validData_ok() throws IOException {
        String resultingReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,20";
        fileWriter.write(resultingReport, VALID_FILE_REPORT);
        assertTrue(Files.exists(Path.of(VALID_FILE_REPORT)));
        assertEquals(resultingReport, Files.readString(Path.of(VALID_FILE_REPORT)));
    }

    @Test
    void write_nullData_notOk() {
        assertThrows(NullPointerException.class, () -> fileWriter.write(null, VALID_FILE_REPORT));
    }

    @Test
    void write_nullFileReport_notOk() {
        String resultingReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,20";
        assertThrows(NullPointerException.class,
                () -> fileWriter.write(resultingReport, null));
    }

    @Test
    void write_invalidFileName_notOk() {
        String resultingReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,20";
        String expectedMessage = "Can not write the file: " + INVALID_FILE_REPORT;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(resultingReport, INVALID_FILE_REPORT));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void write_onlyHeaderReport_ok() throws IOException {
        String resultingReport = "fruit,quantity";
        fileWriter.write(resultingReport, VALID_FILE_REPORT);
        assertTrue(Files.exists(Path.of(VALID_FILE_REPORT)));
        assertEquals(resultingReport, Files.readString(Path.of(VALID_FILE_REPORT)));
    }

    @Test
    void write_emptyReport_ok() throws IOException {
        String resultingReport = "";
        fileWriter.write(resultingReport, VALID_FILE_REPORT);
        assertTrue(Files.exists(Path.of(VALID_FILE_REPORT)));
        assertEquals(resultingReport, Files.readString(Path.of(VALID_FILE_REPORT)));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(VALID_FILE_REPORT));
    }
}
