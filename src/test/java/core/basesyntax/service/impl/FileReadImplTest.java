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
    private FileRead fileRead;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        fileRead = new FileReadImpl();
        tempFile = File.createTempFile("testData", ".txt");
        tempFile.deleteOnExit();
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void readDataFromFile_ReadsLinesSuccessfully() throws IOException {
        String content = "fruit,quantity\napple,10\nbanana,5";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(content);
        }
        List<String> lines = fileRead.readDataFromFile(tempFile.getAbsolutePath());

        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("fruit,quantity", lines.get(0));
        Assertions.assertEquals("apple,10", lines.get(1));
        Assertions.assertEquals("banana,5", lines.get(2));
    }

    @Test
    public void readDataFromFile_FileNotFound_ThrowsRuntimeException() {
        String invalidFileName = "/invalid/path/to/file.txt";
        Assertions.assertThrows(RuntimeException.class, () ->
                fileRead.readDataFromFile(invalidFileName));
    }

    @Test
    public void readDataFromFile_ReadError_ThrowsRuntimeException() {
        String invalidFileName = "nonExistentFile.txt";
        Assertions.assertThrows(RuntimeException.class, () ->
                fileRead.readDataFromFile(invalidFileName));
    }
}
