package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String VALID_FILE_NAME = "src/test/resources/report.csv";
    private static final String INVALID_FILE_NAME = "src/tests/resources/report.csv";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_RegularCase_Ok() throws IOException {
        StringBuilder report = new StringBuilder("fruit,quantity").append(System.lineSeparator())
                        .append("apricot,10").append(System.lineSeparator()).append("banana,20");
        writerService.writeToFile(VALID_FILE_NAME, report.toString());
        String actualFromFile = Files.readString(Path.of(VALID_FILE_NAME));
        assertEquals(report.toString(), actualFromFile);
    }

    @Test
    void writeToFile_InvalidFileName_NotOk() {
        StringBuilder report = new StringBuilder("fruit,quantity").append(System.lineSeparator())
                .append("apricot,10").append(System.lineSeparator()).append("banana,20");
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(INVALID_FILE_NAME,
                report.toString()));
    }
}
