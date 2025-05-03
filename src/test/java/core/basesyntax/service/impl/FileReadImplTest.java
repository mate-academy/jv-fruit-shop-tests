package core.basesyntax.service.impl;

import core.basesyntax.service.FileRead;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReadImplTest {
    private static final String NON_EXISTENT_FILE_NAME = "/invalid/path/to/file.txt";
    private static final String CONTENT = "fruit,quantity\napple,10\nbanana,5";
    private static final String INVALID_FILE_NAME = "nonExistentFile.txt";

    private FileRead fileRead;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        fileRead = new FileReadImpl();
        tempFile = File.createTempFile("testData", ".txt");
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void readDataFromFile_ReadsLinesSuccessfully() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(CONTENT);
        }
        List<String> lines = fileRead.readDataFromFile(tempFile.getAbsolutePath());

        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("fruit,quantity", lines.get(0));
        Assertions.assertEquals("apple,10", lines.get(1));
        Assertions.assertEquals("banana,5", lines.get(2));
    }

    @Test
    public void readDataFromFile_FileNotFound_ThrowsRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileRead.readDataFromFile(NON_EXISTENT_FILE_NAME));
    }

    @Test
    public void readDataFromFile_ReadError_ThrowsRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileRead.readDataFromFile(INVALID_FILE_NAME));
    }
}
