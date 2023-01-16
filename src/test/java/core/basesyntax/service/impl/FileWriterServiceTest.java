package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90" + System.lineSeparator();
    private static final String PATH_TO_FILE = "src/test/resources/report.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        fileWriterService.writeToFile(REPORT, PATH_TO_FILE);
        try {
            String actual = Files.readString(Path.of(PATH_TO_FILE));
            Assert.assertEquals("Expected report " + actual + " in file "
                    + PATH_TO_FILE, REPORT, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        try {
            fileWriterService.writeToFile(REPORT, null);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't write file - path to file is null", e);
        }
    }

    @Test
    public void writeToFile_emptyData_ok() {
        fileWriterService.writeToFile("", PATH_TO_FILE);
        try {
            String actual = Files.readString(Path.of(PATH_TO_FILE));
            Assert.assertEquals("Should write empty report for empty data, but was "
                    + actual, "", actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullData_notOk() {
        fileWriterService.writeToFile(null, PATH_TO_FILE);
        try {
            String actual = Files.readString(Path.of(PATH_TO_FILE));
            Assert.assertEquals("Expected NullPointerException for null data, but was "
                    + actual,null, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test(expected = InvalidPathException.class)
    public void writeToFile_isNotValidPath_notOk() {
        String pathIsNotValid = "&^%$^&*(";
        fileWriterService.writeToFile(REPORT, pathIsNotValid);
        try {
            String actual = Files.readString(Path.of(pathIsNotValid));
            Assert.assertEquals("Expected InvalidPathException for wrong path to file, but was "
                    + actual, REPORT, pathIsNotValid);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
