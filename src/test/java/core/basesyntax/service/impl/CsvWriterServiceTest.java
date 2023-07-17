package core.basesyntax.service.impl;

import core.basesyntax.service.DataWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterServiceTest {
    private static final String INVALID_PATH = "src/test/resources/dummy-path/dummy.csv";
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private static final String REPORT_DATA = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90";
    private DataWriterService dataWriterService;

    @BeforeEach
    void setUp() {
        dataWriterService = new CsvWriterService();
    }

    @Test
    void writeData_invalidPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> dataWriterService.writeData(REPORT_DATA, INVALID_PATH));
    }

    @Test
    void writeData_validPath_ok() {
        dataWriterService.writeData(REPORT_DATA, VALID_PATH);
        List<String> retrievedLines;
        try {
            retrievedLines = Files.readAllLines(Path.of(VALID_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can not read file. Path: " + VALID_PATH);
        }
        String actual = retrievedLines.stream()
                .collect(Collectors.joining(System.lineSeparator()))
                .trim();
        Assertions.assertEquals(REPORT_DATA, actual);
    }
}
