package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;
    private static String reportData;

    @BeforeAll
    static void beforeAll() {
        StringBuilder builder = new StringBuilder();
        writerService = new WriterServiceImpl();
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        reportData = builder.toString();
    }

    @Test
    void writeToFile_invalidFilePathCannotCreateFile_notOk() {
        String pathToFile = "//";
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(reportData, pathToFile));
    }

    @Test
    void writeToFile_emptyFilePathCannotCreateFile_notOk() {
        String pathToFile = "";
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(reportData, pathToFile));
    }

    @Test
    void writeToFile_correctWrittenReport_Ok() {
        String pathToFile = "src/main/resources/report.csv";
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        String expected = builder.toString();
        writerService.writeToFile(reportData, pathToFile);
        String actual;
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(pathToFile));
            actual = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }
}
