package core.basesyntax.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileServiceImplTest {
    private static WriteToFileService writeToFileService;
    private static String actual;

    @BeforeClass
    public static void beforeAll() {
        writeToFileService = new WriteToFileServiceImpl();
        actual = "This is a test message";
    }

    @Test
    public void writeToFile_Ok() {
        String test;
        writeToFileService.writeToFile(actual,
                "src/test/java/core/basesyntax/services/resources/WriteToFile.csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "src/test/java/core/basesyntax/services/resources/WriteToFile.csv"))) {
            test = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Incorrect file name ", e);
        }
        Assert.assertEquals(actual, test);
    }

    @Test
    public void writeToFile_NullString_NotOk() {
        try {
            writeToFileService.writeToFile(null,
                    "src/test/java/core/basesyntax/services/resources/WriteToFile.csv");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("RuntimeException should be thrown when report string is null");
    }

    @Test
    public void writeToFile_NullPath_NotOk() {
        try {
            writeToFileService.writeToFile(actual, null);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("RuntimeException should be thrown when file name is null");
    }
}
