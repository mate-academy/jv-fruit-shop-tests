package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteReportImplTest {
    private static final String FILE_PATH = "finalReportTest.csv";

    private WriteReport writeReport;

    @BeforeEach
    void setUp() {
        writeReport = new WriteReportImpl();
    }

    @AfterEach
    void delete() throws IOException {
        Files.deleteIfExists(Path.of(FILE_PATH));
    }

    @Test
    void writeReport_validData_ok() throws IOException {
        String reportData = "fruit,quantity" + System.lineSeparator() + "apple,10";

        writeReport.writeReport(reportData, FILE_PATH);

        String content = Files.readString(Path.of(FILE_PATH));

        assertEquals(reportData, content);
    }

    @Test
    void writeReport_nullReport_throwException() {
        String reportData = null;

        assertThrows(RuntimeException.class, () -> {
            writeReport.writeReport(reportData, FILE_PATH);
        });
    }

    @Test
    void writeReoport_nullFilePath_throwException() {
        String reportData = "fruit,quantity" + System.lineSeparator() + "apple,10";

        assertThrows(RuntimeException.class, () -> {
            writeReport.writeReport(reportData, null);
        });
    }

    @Test
    void writeReport_cannotWriteToFile_throwException() {
        String reportData = "fruit,quantity" + System.lineSeparator() + "apple,10";

        assertThrows(RuntimeException.class, () -> {
            writeReport.writeReport(reportData, "");
        });
    }

}
