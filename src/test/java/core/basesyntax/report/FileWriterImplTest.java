package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String WRITE_FILE_TEST = "src/test/java/resources/reportFinalTest.csv";
    private final FileWriterCustom fileWriter = new FileWriterImpl();

    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        fileWriter.write("", WRITE_FILE_TEST);
    }

    @Test
    void write_validReport_writesToFile() throws IOException {
        Map<String, Integer> storage = Map.of("banana", 50, "orange", 30, "apple", 100);
        Storage.setStorage(storage);
        String resultingReport = reportGenerator.getReport(Storage.getStorage());
        fileWriter.write(resultingReport, WRITE_FILE_TEST);
        String fileContent = Files.readString(Path.of(WRITE_FILE_TEST));
        assertEquals(resultingReport, fileContent);
    }

    @Test
    void write_emptyReport_writesToFile() throws IOException {
        String emptyReport = reportGenerator.getReport(Map.of());
        fileWriter.write(emptyReport, WRITE_FILE_TEST);
        String fileContent = Files.readString(Path.of(WRITE_FILE_TEST));
        assertEquals(emptyReport, fileContent);
    }

    @Test
    void write_invalidFile_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> fileWriter.write("report",
                "/invalid/path/file.csv"));
    }
}
