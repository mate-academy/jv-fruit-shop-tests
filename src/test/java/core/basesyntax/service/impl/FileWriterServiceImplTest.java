package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidPathException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String OUTPUT_PATH = "src/test/resources/test_output.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @AfterClass
    public static void clearResult() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file after test", e);
        }
    }

    @Test
    public void writeToFile_validPath_ok() {
        String expected = "some test data";
        fileWriterService.writeToFile(OUTPUT_PATH, expected);
        String actual = readFromFile(OUTPUT_PATH);
        assertEquals(String.format("File: %s should contain:%n%s%n but was:%n%s",
                OUTPUT_PATH, expected, actual), expected, actual);
    }

    @Test(expected = InvalidPathException.class)
    public void writeToFile_nullPath_ok() {
        String expected = "some test data";
        fileWriterService.writeToFile(null, expected);
    }

    private String readFromFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + path, e);
        }
    }
}
