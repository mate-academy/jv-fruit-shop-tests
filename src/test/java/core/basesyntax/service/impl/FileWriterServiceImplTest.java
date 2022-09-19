package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;

public class FileWriterServiceImplTest {
    FileWriterService writerService = new FileWriterServiceImpl();

    @Test
    public void writeToFile_pathToFileIsEmpty_notOk() {
        String pathToFile = "";
        String report = "fruit, quantity";
        try {
            writerService.write(pathToFile, report);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_pathToFileIsNull_notOk() {
        String pathToFile = null;
        String report = "fruit, quantity";
        writerService.write(pathToFile, report);
    }

    @Test
    public void writeToFile_reportIsWrittenToFile_Ok() {
        File file = new File("src/main/resources/daily_report.csv");
        String report = "report";
        writerService.write(String.valueOf(file), report);
        Assert.assertTrue(file.canWrite());
    }
}
