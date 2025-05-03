package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterImplTest {
    private static final String VALID_FILE_NAME = "src/test/resources/testWrite.csv";
    private static final String INVALID_FILE_NAME = "";
    private static final String REPORT = "Name, Age, Email\nJohn, 30, john@example.com";
    private CsvWriterImpl csvWriter;

    @BeforeEach
    void setUp() {
        csvWriter = new CsvWriterImpl();
    }

    @Test
    void write_validPath_ok() throws IOException {
        File tempFile = new File(VALID_FILE_NAME);
        csvWriter.write(tempFile.getAbsolutePath(), REPORT);
        String actualContent = Files.readString(tempFile.toPath());
        assertEquals(REPORT, actualContent);
    }

    @Test
    void write_invalidPath_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> csvWriter.write(INVALID_FILE_NAME, REPORT));
        assertEquals("Can't write report to file ", runtimeException.getMessage());
    }
}
