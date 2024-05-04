package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String FILE_PATH = "src/main/resources/output.csv";
    private WriterService writerService;
    private List<String> originalContent;

    @BeforeEach   
    void setUp() throws IOException {
        writerService = new WriterServiceImpl();
        originalContent = Files.readAllLines(Path.of(FILE_PATH));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.write(Paths.get(FILE_PATH), originalContent);
    }

    @Test
    void writeToFile_validInput_writesToFile() throws IOException {
        List<String> report = Arrays.asList("line1", "line2", "line3");
        writerService.writeToFile(FILE_PATH, report);
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        assertEquals(report, lines);
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        List<String> report = Arrays.asList("line1", "line2", "line3");
        assertThrows(RuntimeException.class, () -> writerService.writeToFile("", report));
    }

    @Test
    void writeToFile_nullReport_throwsException() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(FILE_PATH, null));
    }
}
