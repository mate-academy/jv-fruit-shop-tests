package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String VALUE_FILE_PATH = "src/test/java/resourcesTest/outputFile.csv";
    private static final String ACTUAL_FILE_DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_incorrectPath_ok() {
        String path = VALUE_FILE_PATH;
        String expected = ACTUAL_FILE_DATA;
        String actual = readFile(path);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_presentFile_ok() {
        String path = VALUE_FILE_PATH;
        File file = new File(path);
        Assert.assertTrue(file.exists());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        fileWriterService.writeToFile(ACTUAL_FILE_DATA, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullData_NotOk() {
        fileWriterService.writeToFile(null, ACTUAL_FILE_DATA);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyData_NotOk() {
        fileWriterService.writeToFile("", ACTUAL_FILE_DATA);
    }

    public String readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
            return builder.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file" + filePath, e);
        }
    }
}
