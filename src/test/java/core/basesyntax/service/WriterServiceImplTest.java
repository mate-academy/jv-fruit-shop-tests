package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void write_invalidFilePath_notOk() {
        String report = "Test report content";
        String invalidFilePath = TEST_RESOURCES_PATH + "\0-invalid-.txt";
        assertThrows(RuntimeException.class, () -> writerService.write(report, invalidFilePath),
                "invalid file path");
    }

    @Test
    void write_nullReportOrFilePath_notOk() {
        String report = "test report";
        String fileName = TEST_RESOURCES_PATH + "test-report.txt";
        assertThrows(RuntimeException.class, () -> writerService.write(null, fileName),
                "A null report");
        assertThrows(RuntimeException.class, () -> writerService.write(report, null),
                "A null file path");
    }
}
