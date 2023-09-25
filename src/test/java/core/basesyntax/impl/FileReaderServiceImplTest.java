package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReaderService = new FileReaderServiceImpl();
        tempFile = File.createTempFile("testFile", ".txt");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("Line 1\nLine 2\nLine 3");
        writer.close();
    }

    @Test
    void testReadDataFromFile() throws IOException {
        List<String> data = fileReaderService.readData(tempFile.getPath());
        assertEquals(3, data.size());
        assertEquals("Line 1", data.get(0));
        assertEquals("Line 2", data.get(1));
        assertEquals("Line 3", data.get(2));
    }

    @Test
    void testReadDataFromNonExistentFile() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readData("nonexistentfile.txt"));
    }

    @Test
    void testReadDataFromEmptyFile() throws IOException {
        File emptyFile = File.createTempFile("emptyFile", ".txt");
        List<String> data = fileReaderService.readData(emptyFile.getPath());
        assertEquals(0, data.size());
    }
}
