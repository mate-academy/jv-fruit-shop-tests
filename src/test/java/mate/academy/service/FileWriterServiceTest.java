package mate.academy.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import mate.academy.service.impl.CsvFileWriterServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String FILE_NAME = "resultFile.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new CsvFileWriterServiceImpl();
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    public void fileWrite_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator() + "apple,25" + System.lineSeparator();
        fileWriterService.write(report, FILE_NAME);
        String actualResult = readFromFile(FILE_NAME);
        Assert.assertEquals(actualResult, report);
    }

    @Test
    public void fileEmptyWrite_ok() {
        String report = "";
        fileWriterService.write(report, FILE_NAME);
        String actualResult = readFromFile(FILE_NAME);
        Assert.assertEquals(actualResult, report);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
