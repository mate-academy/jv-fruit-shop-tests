package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static Writer writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriterImpl();
    }

    @Test
    void writeToFile_nullValues_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(null, null));
        assertThrows(RuntimeException.class, () -> writer.writeToFile("report", null));
    }

    @Test
    void writeToFile_rightData_ok() {
        String filePath = "src/test/resources/report.csv";
        writer.writeToFile("report", filePath);
        assertTrue(Files.exists(Path.of(filePath)));
    }
}
