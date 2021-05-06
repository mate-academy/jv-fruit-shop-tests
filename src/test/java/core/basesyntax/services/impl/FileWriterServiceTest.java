package core.basesyntax.services.impl;

import core.basesyntax.services.FileWriterService;
import org.junit.Test;

public class FileWriterServiceTest {
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void fileWriterTestExistingPath_OK() {
        fileWriterService.write("", "src/test/resources/TestWriter.csv");
    }

    @Test(expected = RuntimeException.class)
    public void fileWriterTestNotExistPath_OK() {
        fileWriterService.write("", "src/st/resodsurces/TestWriter.csv");
    }
}
