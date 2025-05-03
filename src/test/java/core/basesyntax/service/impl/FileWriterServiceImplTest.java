package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String outputFileName = "src/test/resources/output.csv";
    private static final String nonexistentFileName = "src/test/resources/nonexistent.csv";
    private static FileWriterService fileWriterService;

    @BeforeAll
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_successfulWrite_ok() throws IOException {
        String expected = "Test data";
        fileWriterService.writeToFile(nonexistentFileName, expected);
        Path path = Paths.get(nonexistentFileName);
        byte[] fileBytes = Files.readAllBytes(path);
        String actual = new String(fileBytes);
        assertEquals(expected, actual, "Text in file should be " + expected
                + " but was " + actual);

        expected = "";
        fileWriterService.writeToFile(nonexistentFileName, expected);
        fileBytes = Files.readAllBytes(path);
        actual = new String(fileBytes);
        assertEquals(expected, actual, "Text in file should be " + expected
                + " but was " + actual);
    }

    @Test
    public void writeToFile_fileCreation_ok() throws IOException {
        String text = "Test data";
        fileWriterService.writeToFile(nonexistentFileName, text);
        Path pathToFile = Paths.get(nonexistentFileName);
        Path path = pathToFile.getParent();
        assertTrue(Files.exists(path), "Nonexistent file should be created!");
        Files.delete(pathToFile);
    }

    @Test
    public void readFromFile_nullFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(null, "data"),
                "RuntimeException should be thrown if name file is null!");
    }

    @Test
    public void readFromFile_nullData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(outputFileName, null),
                "RuntimeException should be thrown if data to be written is null!");
    }
}
