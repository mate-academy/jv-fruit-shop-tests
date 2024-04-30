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
    private WriterService writerService;
    private List<String> originalContent;

    private final String filePath = "src/main/resources/output.csv";

    @BeforeEach
    void setUp() throws IOException {
        writerService = new WriterServiceImpl();
        originalContent = Files.readAllLines(Path.of(filePath));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.write(Paths.get(filePath), originalContent);
    }

    @Test
    void writeToFile_validInput_writesToFile() throws IOException {
        List<String> report = Arrays.asList("line1", "line2", "line3");
        writerService.writeToFile(filePath, report);
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(report, lines);
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        List<String> report = Arrays.asList("line1", "line2", "line3");
        assertThrows(RuntimeException.class, () -> writerService.writeToFile("", report));
    }

    @Test
    void writeToFile_nullReport_throwsException() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(filePath, null));
    }
}
