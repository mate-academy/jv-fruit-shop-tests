package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_validFile_ok() {
        fileWriter.write("fruit,quantity\nbanana,100",
                "src/main/resources/testReport.csv");
        assertTrue(new File("src/main/resources/testReport.csv").exists());
    }

    @Test
    void write_invalidPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("content", "invalid/path/file.csv"));
    }
}
