package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String CORRECT_PATH = "output_test.csv";
    private static final String WRONG_PATH = "";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeFile_Ok() {
        Assert.assertFalse("The file had to be saved.",
                fileWriterService.writeToFile(CORRECT_PATH, ""));
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongPath_NotOk() {
        fileWriterService.writeToFile(WRONG_PATH, "");
    }
}
