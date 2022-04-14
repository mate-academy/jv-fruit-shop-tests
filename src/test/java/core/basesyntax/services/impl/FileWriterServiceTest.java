package core.basesyntax.services.impl;

import core.basesyntax.services.FileWriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String VALID_FILE_PATH = "src/test/resources/TestWriter.csv";
    private static final String INVALID_FILE_PATH = "src/st/resodsurces/TestWriter.csv";
    private static final String EMPTY_STRING = "";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void fileWriterTestExistingPath_OK() {
        fileWriterService.write(EMPTY_STRING, VALID_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriterTestNotExistPath_OK() {
        fileWriterService.write(EMPTY_STRING, INVALID_FILE_PATH);
    }
}
