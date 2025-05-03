package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class FileWriterServiceCsvImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
    private static final Set<String> REPORT = new HashSet<>();
    private static final WriterServiceImpl fileWriterService = new WriterServiceImpl();

    @Test
    void writeData_validPath_ok() {
        REPORT.add("report");

        assertDoesNotThrow(() -> fileWriterService.writeToFile(OUTPUT_FILE_PATH, REPORT));
    }

    @Test
    void writeData_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriterService.writeToFile("", REPORT));
    }
}
