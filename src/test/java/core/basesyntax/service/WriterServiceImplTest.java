package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidFilePath_notOk() {
        String report = "Test report content";
        String invalidFilePath = TEST_RESOURCES_PATH + "\0-invalid-.txt";
        writerService.write(report, invalidFilePath);
    }

    @Test
    public void write_validReport_Ok() {
        String report = "Test report content";
        String fileName = TEST_RESOURCES_PATH + "test-report.txt";
        writerService.write(report, fileName);
        Path filePath = Paths.get(fileName);
        String fileContent;
        try {
            fileContent = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("File content should match the report", report, fileContent);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullFilePath_notOk() {
        String report = "test report";
        writerService.write(report, null);
    }
}
