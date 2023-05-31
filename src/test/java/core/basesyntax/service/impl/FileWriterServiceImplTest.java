package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT_PATH = "src/test/resources/reportTest.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @AfterClass
    public static void clearResult() {
        try {
            Files.deleteIfExists(Path.of(REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file after test", e);
        }
    }

    @Test(expected = InvalidPathException.class)
    public void writeToFile_nullPath_ok() {
        String expected = "text for test";
        fileWriterService.writeReport(null, expected);
    }

    @Test
    public void writeToFile_validPath_ok() {
        String expected = "text for test";
        fileWriterService.writeReport(REPORT_PATH, expected);
        String actual = readFile();
        assertEquals(String.format("File: %s should contain:%n%s%n but was:%n%s",
                REPORT_PATH, expected, actual), expected, actual);
    }

    private String readFile() {
        try {
            return Files.readString(Path.of(FileWriterServiceImplTest.REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file "
                    + FileWriterServiceImplTest.REPORT_PATH, e);
        }
    }
}
