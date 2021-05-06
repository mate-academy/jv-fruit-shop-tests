package core.basesyntax.services.impl;

import core.basesyntax.services.FileWriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void fileWriterTestExistingPath_OK() {
        fileWriterService.write("", "src/test/resources/TestWriter.csv");
    }

    @Test(expected = RuntimeException.class)
    public void fileWriterTestNotExistPath_OK() {
        fileWriterService.write("", "src/st/resodsurces/TestWriter.csv");
    }
}
