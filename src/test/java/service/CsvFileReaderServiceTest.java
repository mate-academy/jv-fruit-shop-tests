package service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static FileReaderService fileReaderService;
    private static final String PATH = "src" + File.separator
                                    + "test" + File.separator
                                    + "java" + File.separator
                                    + "resources";
    private static final File TEST_FILE =
            new File(PATH + File.separator + "testFile.csv");
    private static final File NOT_EXISTED_FILE =
            new File(PATH + File.separator + "notExistedFile.csv");

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new CsvFileReaderService();
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
    public void readFile_withData_Ok() {
        List<String> expected = new ArrayList<>();
        String data;
        for (int i = 1; i <= 10; i++) {
            data = i + " operation, fruit, amount";
            writeDataToFile(data);
            expected.add(data);
        }
        List<String> actual = fileReaderService.readFile(TEST_FILE);
        Assert.assertEquals("Result is incorrect", expected, actual);
    }

    @Test
    public void readFile_withoutData_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.readFile(TEST_FILE);
        Assert.assertEquals("Result is incorrect", expected, actual);
    }

    @Test
    public void readFile_null_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.readFile(null);
        Assert.assertEquals("Result is incorrect", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_notExistedFile_NotOk() {
        fileReaderService.readFile(NOT_EXISTED_FILE);
        Assert.fail("Should throw RuntimeException if file doesn't exist.");
    }

    private void writeDataToFile(String data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TEST_FILE, true))) {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file." + e);
        }
    }
}
