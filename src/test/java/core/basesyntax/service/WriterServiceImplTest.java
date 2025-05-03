package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana, 152" + System.lineSeparator()
            + "apple, 90";

    @BeforeAll
    static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeReportToCsvFile_invalidFilePath_notOk() {
        String pathToFile = "src/test/resources-invalidPath";
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> writerService.writeReportToCsvFile(REPORT, pathToFile));
        assertEquals("The file does not exist" + pathToFile,
                runtimeException.getMessage());
    }

    @Test
    void writeReportToCsvFile_nullPath_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writerService.writeReportToCsvFile(REPORT, null));
        assertEquals("File path cannot be null", exception.getMessage());
    }

    @Test
    void writeReportToCsvFile_validPath_Ok() {
        String pathToFile = "src/test/resources/report";
        Assertions.assertDoesNotThrow(() -> writerService
                .writeReportToCsvFile(REPORT, pathToFile));
        List<String> readFromFile = readFromFile(pathToFile);
        String actualReport = String.join(System.lineSeparator(), readFromFile);
        assertEquals(actualReport,REPORT);
    }

    private List<String> readFromFile(String filePath) {
        List<String> dataFromFile;
        try {
            Path path = Paths.get(filePath);
            dataFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("It is not possible to read data from the file via"
                    + " this path - " + filePath);
        }
        return dataFromFile;
    }
}
