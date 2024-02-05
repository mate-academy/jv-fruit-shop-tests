package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final String REPORT_FILE_PATH = "src/test/resources/report.csv";
    private final Writer writer = new WriterImpl();

    @Test
    void writeToFile_nullValues_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(null, null));
        assertThrows(RuntimeException.class, () -> writer.writeToFile("report", null));
    }

    @Test
    void writeToFile_rightData_ok() {
        writer.writeToFile("report", REPORT_FILE_PATH);
        assertTrue(Files.exists(Path.of(REPORT_FILE_PATH)));
    }
}
