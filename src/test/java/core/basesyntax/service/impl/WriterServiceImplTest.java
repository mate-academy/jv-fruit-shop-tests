package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String VALID_FILE_NAME = "src/test/resources/report.csv";
    private WriterService writerService;
    private List<String> lines;

    private List<String> createNewTestLines() {
        List<String> newLines = new ArrayList<>();
        newLines.add("fruit,quantity");
        newLines.add(System.lineSeparator() + "banana,20");
        newLines.add(System.lineSeparator() + "apple,10");
        return newLines;
    }

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
        lines = createNewTestLines();
    }

    @Test
    void writeToFile_nullPath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> writerService.writeToFile(null, lines));
    }

    @Test
    void writeToFile_validDataAndPath_ok() throws IOException {
        writerService.writeToFile(VALID_FILE_NAME, lines);
        List<String> actualLines = Files.readAllLines(Path.of(VALID_FILE_NAME));
        assertLinesMatch(lines, actualLines);
    }
}
