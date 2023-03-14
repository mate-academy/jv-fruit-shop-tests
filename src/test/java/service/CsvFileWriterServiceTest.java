package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static FileWriterService fileWriterService;
    private static final String PATH = "src" + File.separator
                                    + "test" + File.separator
                                    + "resources";
    private static final File TEST_FILE =
            new File(PATH + File.separator + "testFile.csv");

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new CsvFileWriterService();
    }

    @Before
    public void setUp() {
        try {
            TEST_FILE.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create directory with resources." + e);
        }
    }

    @After
    public void tearDown() {
        TEST_FILE.delete();
    }

    @Test
    public void saveToFile_someData_Ok() {
        List<String> expected = new ArrayList<>();
        String data;
        for (int i = 1; i <= 10; i++) {
            data = i + " operation, fruit, amount";
            expected.add(data);
        }
        File resultFile = fileWriterService.saveToFile(TEST_FILE, expected);
        List<String> actual = readFile(resultFile);
        Assert.assertEquals("Result is incorrect", expected, actual);
    }

    @Test
    public void saveToFile_emptyData_Ok() {
        List<String> expected = new ArrayList<>();
        File resultFile = fileWriterService.saveToFile(TEST_FILE, expected);
        List<String> actual = readFile(resultFile);
        Assert.assertEquals("Result is incorrect", expected, actual);
    }

    @Test
    public void saveToFile_nullData_Ok() {
        File resultFile = fileWriterService.saveToFile(TEST_FILE, null);
        List<String> expected = new ArrayList<>();
        List<String> actual = readFile(resultFile);
        Assert.assertEquals("Result is incorrect", expected, actual);
    }

    @Test
    public void saveToFile_nullFile_NotOk() {
        List<String> someData = List.of("line1", "line2", "line3");
        try {
            File resultFile = fileWriterService.saveToFile(null, someData);
        } catch (RuntimeException e) {
            return;
        }
        Assert.assertEquals("Should throw RuntimeException if null instead of file.",
                true, false);
    }

    private List<String> readFile(File inputFile) {
        try {
            return Files.readAllLines(inputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can’t read file", e);
        }
    }
}