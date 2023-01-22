package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

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
    public void write_Ok() {
        Assert.assertFalse("The file had to be saved.",
                fileWriterService.writeToFile(CORRECT_PATH, ""));
    }

    @Test
    public void write_NotOk() {
        try {
            fileWriterService.writeToFile(WRONG_PATH, "");
        } catch (RuntimeException e) {
            return;
        }
        fail("You should throw an exception for empty path.");
    }
}
