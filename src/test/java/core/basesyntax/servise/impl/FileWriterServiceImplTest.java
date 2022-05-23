package core.basesyntax.servise.impl;

import core.basesyntax.servise.FileWriterService;
import java.io.File;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void write_toFile_Ok() {
        File file = new File("src/main/resources/report_test.csv");
        String report = "report";
        fileWriterService.write(String.valueOf(file), report);
        Assert.assertTrue(file.canWrite());
    }

    @Test
    public void write_toFileWithNullPath_notOk() {
        try {
            fileWriterService.write(null, "Unreal");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void write_toFileWithEmptyPath_notOk() {
        try {
            fileWriterService.write("", "Unreal");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }
}
