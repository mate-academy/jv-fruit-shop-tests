package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String REPORT_PATH = "src/test/java/resources/reportTest.csv";
    private final WriterService writerService;

    private WriterServiceImplTest() {
        writerService = new WriterServiceImpl();
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.delete(Path.of(REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + REPORT_PATH);
        }
    }

    @Test
    void writeToFile_dataNotNull_ok() {
        String report = "banana,25" + System.lineSeparator() + "apple,40" + System.lineSeparator();
        writerService.writeToFile(report, REPORT_PATH);
        Assertions.assertTrue(Files.exists(Path.of(REPORT_PATH)));
    }

    @Test
    void writeToFile_fileNotExists_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                writerService.writeToFile(null, null)
        );
    }
}
