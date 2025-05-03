package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90" + System.lineSeparator();
    private static final String VALID_PATH_TO_FILE = "src/test/resources/report.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void init() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        fileWriterService.writeToFile(REPORT, VALID_PATH_TO_FILE);
        try {
            String actual = Files.readString(Path.of(VALID_PATH_TO_FILE));
            Assert.assertEquals("Expected report " + actual + " in file "
                    + VALID_PATH_TO_FILE, REPORT, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_notOk() {
        fileWriterService.writeToFile(REPORT, null);

    }

    @Test
    public void writeToFile_emptyData_ok() {
        fileWriterService.writeToFile("", VALID_PATH_TO_FILE);
        try {
            String actual = Files.readString(Path.of(VALID_PATH_TO_FILE));
            Assert.assertEquals("Should write empty report for empty data, but was "
                    + actual, "", actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullData_notOk() {
        fileWriterService.writeToFile(null, VALID_PATH_TO_FILE);
        try {
            String actual = Files.readString(Path.of(VALID_PATH_TO_FILE));
            Assert.assertNull("Expected NullPointerException for null data, but was "
                    + actual, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test
    public void writeToFile_isNotValidPath_notOk() {
        String pathIsNotValid = "null";
        fileWriterService.writeToFile(REPORT, pathIsNotValid);
        try {
            Files.readString(Path.of(pathIsNotValid));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
