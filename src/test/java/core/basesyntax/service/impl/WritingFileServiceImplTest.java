package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WritingFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WritingFileServiceImplTest {
    private static WritingFileService writingFileService;
    private static final String OUTPUT_FILE = "src/test/java/resources/fruits_report.csv";
    private static final String INVALID_FILE_PATH = "*src/test/java/resources/invalid_data.csv";
    private static final String REPORT_TEST_DATA = "WRITING_TEST";
    private static final String CSV_SEPARATOR = "";
    private static String reportData;
    private static String actual;

    @BeforeAll
    static void beforeAll() throws IOException {
        writingFileService = new WritingFileServiceImpl();
        List<String> readData = Files.readAllLines(Paths.get(OUTPUT_FILE));
        actual = readData.stream()
                .map(Object::toString)
                .collect(Collectors.joining(CSV_SEPARATOR));
        reportData = "fruit,quantity" + System.lineSeparator()
                + "banana,200" + System.lineSeparator()
                + "apple,150" + System.lineSeparator();
    }

    @Test
    void invalidWritingDataToFile_Ok() {
        writingFileService.writingDataToFile(REPORT_TEST_DATA, OUTPUT_FILE);
        assertEquals(REPORT_TEST_DATA, actual);
    }

    @Test
    void invalidWritingDataToFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writingFileService.writingDataToFile(reportData, INVALID_FILE_PATH));
    }
}
