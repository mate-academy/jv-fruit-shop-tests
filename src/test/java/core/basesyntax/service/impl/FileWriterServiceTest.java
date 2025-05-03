package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/DailyReport.csv";
    private static final String WRONG_FILE_PATH = "someNonExistingFolder/someNonExistingFile.xyz";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static FileWriterService fileWriterService;
    private static String testData;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
        testData = "fruit,quantity"
                + LINE_SEPARATOR + "banana,152"
                + LINE_SEPARATOR + "apple,90";
    }

    @Test
    public void writeToFile_correctPath_ok() throws IOException {
        fileWriterService.writeToFile(testData, CORRECT_FILE_PATH);
        List<String> dataFromFile;
        dataFromFile = Files.readAllLines(Path.of(CORRECT_FILE_PATH));
        List<String> expectedData = new ArrayList<>(Arrays.asList(testData.split(LINE_SEPARATOR)));
        Assert.assertEquals(expectedData, dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_incorrectPath_notOk() {
        fileWriterService.writeToFile(testData, WRONG_FILE_PATH);
    }
}
