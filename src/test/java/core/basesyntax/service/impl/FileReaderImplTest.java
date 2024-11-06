package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String NON_EXISTENT_FILE_NAME = "/invalid/file.csv";
    private static final String CONTENT = "fruit,quantity" + System.lineSeparator()
            + "apple,10" + System.lineSeparator()
            + "banana,5";
    private static final String INVALID_FILE_NAME = "nonExistentFile.csv";
    private FileReader fileReader;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        tempFile = File.createTempFile("testData", ".csv");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void readDataFromFile_success() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))) {
            bufferedWriter.write(CONTENT);
        }
        List<String> lines = fileReader.read(tempFile.getAbsolutePath());
        assertEquals(3, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("apple,10", lines.get(1));
        assertEquals("banana,5", lines.get(2));
    }

    @Test
    void readDataFromFile_fileNotFound_trowsException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(NON_EXISTENT_FILE_NAME));
    }

    @Test
    void readDataFromFile_readError_trowsException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(INVALID_FILE_NAME));
    }
}
