package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private FileWriterService writerService;

    @Before
    public void before() {
        writerService = new FileWriterServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_pathToFileIsEmpty_notOk() {
        String pathToFile = "";
        String report = "fruit, quantity";
        writerService.write(report, pathToFile);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_pathToFileIsNull_notOk() {
        String pathToFile = null;
        String report = "fruit, quantity";
        writerService.write(report, pathToFile);
    }

    @Test
    public void writeToFile_reportIsWrittenToFile_Ok() {
        File file = new File("src/main/resources/daily_report.csv");
        String report = "report";
        writerService.write(String.valueOf(file), report);
        Assert.assertTrue(file.canWrite());
    }
}
