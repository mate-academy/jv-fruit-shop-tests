package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriterService;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private String report;
    private String filePath;
    private FileWriterService writerService;

    @BeforeEach
    void setUp() {
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator();
        filePath = "src/test/resources/finalReport.csv";
        writerService = new FileWriterServiceImpl();
    }

    @Test
    void writeReport_Ok() {
        writerService.write(report, filePath);
        assertTrue(Files.exists(Path.of(filePath)));
    }

    @Test
    void writeInvalidFilePath_NotOk() {
        String actualPath = "invalid/file/path/finalReport.csv";
        assertThrows(RuntimeException.class, () -> writerService.write(report, actualPath));
    }

    @Test
    void writeInvalidReport_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(null, filePath);
        });
    }
}
