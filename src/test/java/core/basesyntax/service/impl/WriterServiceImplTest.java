package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String INVALID_NAME_OF_FILE = "src/test/resources/abs.csv";
    private static final String VALID_FILE_NAME = "src/test/resources/report.csv";
    private static WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_nullPathAndReport_notOk() {
        assertThrows(NullPointerException.class,
                () -> writerService.writeToFile(INVALID_NAME_OF_FILE, null));
    }

    @Test
    void writeToFile_nullReport_notOk() {
        assertThrows(NullPointerException.class,
                () -> writerService.writeToFile(VALID_FILE_NAME, null));
    }

    @Test
    void writeToFile_nullPath_notOk() {
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        lines.add(System.lineSeparator() + "banana,20");
        lines.add(System.lineSeparator() + "apple,10");
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(null, lines));
    }

    @Test
    void writeToFile_validDataAndPath_ok() {
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        lines.add(System.lineSeparator() + "banana,20");
        lines.add(System.lineSeparator() + "apple,10");
        writerService.writeToFile(VALID_FILE_NAME, lines);
        try {
            List<String> fileContent = Files.readAllLines(Paths
                    .get(VALID_FILE_NAME));
            assertEquals(lines, fileContent, "File content doesn't match to expected content");
        } catch (IOException e) {
            throw new RuntimeException("Error of reading file for a validation:" + e);
        }
    }
}