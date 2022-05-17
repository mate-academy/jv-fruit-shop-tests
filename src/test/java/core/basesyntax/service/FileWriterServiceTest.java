package core.basesyntax.service;

import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceTest {
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_pathToFileIsNull_notOk() {
        String pathToFile = null;
        String report = "fruit, quantity";
        fileWriterService.write(pathToFile, report);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_pathToFileIsEmpty_notOk() {
        String pathToFile = "";
        String report = "fruit, quantity";
        fileWriterService.write(pathToFile, report);
    }

    @Test
    public void writeToFile_reportIsWrittenToFile_Ok() {
        File file = new File("src/main/resources/report_test.csv");
        String report = "report";
        fileWriterService.write(String.valueOf(file), report);
        Assert.assertTrue(file.canWrite());
    }
}
