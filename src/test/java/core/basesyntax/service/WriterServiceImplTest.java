package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidFilePath_notOk() {
        String report = "Test report content";
        String invalidFilePath = TEST_RESOURCES_PATH + "\0-invalid-.txt";
        writerService.write(report, invalidFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullReportOrFilePath_notOk() {
        String report = "test report";
        String fileName = TEST_RESOURCES_PATH + "test-report.txt";
        writerService.write(null, fileName);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullFilePath_notOk() {
        String report = "test report";
        writerService.write(report, null);
    }
}
