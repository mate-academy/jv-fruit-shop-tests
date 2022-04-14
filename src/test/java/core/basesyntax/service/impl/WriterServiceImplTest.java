package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.WriterService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/output_test.csv";
    private static final String INCORRECT_PATH = "";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_Ok() {
        Assert.assertTrue("The file had to be saved!", writerService.write(CORRECT_PATH, ""));
    }

    @Test
    public void write_NotOk() {
        try {
            writerService.write(INCORRECT_PATH, "");
        } catch (RuntimeException e) {
            return;
        }
        fail("You should throw an exception for empty path");
    }
}
