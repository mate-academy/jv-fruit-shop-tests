package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String OUTPUT_PATH = "src/test/resources/output.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void writeFile_correctData_Ok() throws IOException {
        String expected = "Test data for file";
        fileWriter.writeFile(OUTPUT_PATH, expected);
        byte[] fileBytes = Files.readAllBytes(Path.of(OUTPUT_PATH));
        String actual = new String(fileBytes);
        assertEquals(expected, actual);
    }

    @Test
    void writeFile_nullFileName_NotOk() {
        String data = "Test data for file";
        assertThrows(RuntimeException.class, () -> fileWriter.writeFile(null, data));
    }

    @Test
    void writeFile_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.writeFile(OUTPUT_PATH, null));
    }
}
