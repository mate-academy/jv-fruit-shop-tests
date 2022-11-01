package core.basesyntax.service;

import core.basesyntax.service.impl.CsvWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvWriterServiceTest {
    private static final String EMPTY_TITLE = null;
    private static final String EXAMPLE_FILE = "write_to.csv";
    private static final String ONE_LINE_TO_FILE_EXAMPLE_1 = "Example line 1";
    private static final String ONE_LINE_TO_FILE_EXAMPLE_2 = "Example line 2";
    private static final String TWO_LINE_TO_FILE = "First line" + System.lineSeparator() + "Second line";
    private static final String TITLE_TO_FILE = "Title";
    private static WriterService writerService;
    private static Path path;


    @BeforeClass
    public static void beforeAll() {
        writerService = new CsvWriter(EMPTY_TITLE);
        path = Path.of(EXAMPLE_FILE);
    }

    @After
    public void afterAll() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Error, can`t delete test file" , e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_writeToNullFile_exception() {
        writerService.saveToFile(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void write_writeNullData_exception() {
        writerService.saveToFile(EXAMPLE_FILE, null);
    }

    @Test
    public void write_writeWithTitle_ok() {
        writerService = new CsvWriter(TITLE_TO_FILE);
        writerService.saveToFile(EXAMPLE_FILE, TWO_LINE_TO_FILE);
        String actual = readFromFile(EXAMPLE_FILE);
        Assert.assertEquals(TITLE_TO_FILE + System.lineSeparator() + TWO_LINE_TO_FILE,
                actual);
        writerService = new CsvWriter(null);
    }

    @Test
    public void write_writeMultipleLines_ok() {
        writerService.saveToFile(EXAMPLE_FILE, TWO_LINE_TO_FILE);
        String actual = readFromFile(EXAMPLE_FILE);
        Assert.assertEquals(TWO_LINE_TO_FILE, actual);
    }

    @Test
    public void write_rewriteLinesFromExistedFile_ok() {
        try {
            Files.createFile(path);
            Files.write(path, ONE_LINE_TO_FILE_EXAMPLE_1.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to test file", e);
        }
        writerService.saveToFile(EXAMPLE_FILE, ONE_LINE_TO_FILE_EXAMPLE_2);
        String actual = readFromFile(EXAMPLE_FILE);
        Assert.assertEquals(ONE_LINE_TO_FILE_EXAMPLE_2, actual);
    }

    @Test
    public void write_writeToNonExistFile_ok() {
        writerService.saveToFile(EXAMPLE_FILE, ONE_LINE_TO_FILE_EXAMPLE_1);
        String actual = readFromFile(EXAMPLE_FILE);
        Assert.assertEquals(actual, ONE_LINE_TO_FILE_EXAMPLE_1);
    }

    private String readFromFile(String pathToFile) {
        Path path = Path.of(pathToFile);
        String result;
        try {
            result = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read test file", e);
        }
        return result;
    }
}
