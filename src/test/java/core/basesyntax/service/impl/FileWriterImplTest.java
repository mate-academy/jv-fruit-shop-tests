package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriterImpl fileWriterImpl;

    @BeforeEach
    void beforeEach() {
        fileWriterImpl = new FileWriterImpl();
    }

    @Test
    void writeReportToFile_correctPath_ok() {

        fileWriterImpl.writeReportToFile("Report", "testOk");
        File file = new File("target/testOk");
        assertTrue(file.exists());
        try {
            String actual = Files.readString(file.toPath());
            assertEquals("Report" + System.lineSeparator(), actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file.delete();
    }

    @Test
    void writeReportToFile_emptyFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterImpl.writeReportToFile("Report", ""));
    }

    @Test
    void writeReportToFile_nullFileName_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterImpl.writeReportToFile("Report", null));
    }

}
