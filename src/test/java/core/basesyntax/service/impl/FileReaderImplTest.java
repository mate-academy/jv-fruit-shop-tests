package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderImpl fileReaderImpl;

    @BeforeEach
    void setUp() {
        fileReaderImpl = new FileReaderImpl();
    }

    @Test
    void read_nullFileName_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReaderImpl.read(null));
        assertEquals("Unable to read file: FileName should not be null",
                exception.getMessage());
    }

    @Test
    void read_nonExistingFile_notOk() {
        String wrongFile = "src/test/resources/non_existing_file.csv";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReaderImpl.read(wrongFile));
        assertTrue(exception.getMessage().contains("Failed to read file"));
    }

    @Test
    void read_validFile_ok() throws IOException {
        File tempFile = File.createTempFile("test-file", ".csv");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("apple,10" + System.lineSeparator() + "banana,20");
        }
        List<String> actual = fileReaderImpl.read(tempFile.getAbsolutePath());
        assertEquals(2, actual.size());
        assertEquals("apple,10", actual.get(0));
        assertEquals("banana,20", actual.get(1));
    }
}
