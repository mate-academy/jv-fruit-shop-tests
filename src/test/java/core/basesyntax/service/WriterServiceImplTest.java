package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WriterServiceImplTest {
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void write_invalidFilePath_notOk() {
        String report = "Test report content";
        String invalidFilePath = "\0-invalid-.txt";
        assertThrows(RuntimeException.class, () -> writerService.write(report, invalidFilePath),
                "invalid file path");
    }

    @Test
    void write_nullReportOrFilePath_notOk() {
        String report = "test report";
        String fileName = "test-report.txt";
        assertThrows(NullPointerException.class, () -> writerService.write(null, fileName),
                "A null report");
        assertThrows(NullPointerException.class, () -> writerService.write(report, null),
                "A null file path");
    }
}