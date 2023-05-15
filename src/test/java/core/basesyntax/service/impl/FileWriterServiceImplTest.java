package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/testWriterFile.csv";
    private static final String INCORRECT_FILE_PATH = "";
    private static final String TEST_REPORT = "apple, 120";
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void write_correctPath_ok() {
        fileWriterService.write(TEST_REPORT, CORRECT_FILE_PATH);
        Assert.assertTrue(Files.exists(Path.of(CORRECT_FILE_PATH)));
    }

    @Test(expected = RuntimeException.class)
    public void write_incorrectPath_notOk() {
        fileWriterService.write(TEST_REPORT, INCORRECT_FILE_PATH);
    }
}
