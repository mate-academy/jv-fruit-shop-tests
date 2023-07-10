package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WritingFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class WritingFileServiceImplTest {
    private static final WritingFileService writingFileService = new WritingFileServiceImpl();
    private static final String OUTPUT_FILE = "src/test/java/resources/fruits_report.csv";
    private static final String INVALID_FILE_PATH = "*src/test/java/resources/invalid_data.csv";
    private static final String REPORT_TEST_DATA = "WRITING_TEST";

    @Test
    void invalidWritingDataToFile_Ok() throws IOException {
        String reportTestData = REPORT_TEST_DATA;
        writingFileService.writingDataToFile(reportTestData, OUTPUT_FILE);
        List<String> readData = Files.readAllLines(Paths.get(OUTPUT_FILE));
        String actual = readData.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
        assertEquals(reportTestData, actual);
    }

    @Test
    void invalidWritingDataToFile_NotOk() {
        String reportData = "fruit,quantity" + System.lineSeparator()
                + "banana,200" + System.lineSeparator()
                + "apple,150" + System.lineSeparator();
        assertThrows(RuntimeException.class,
                () -> writingFileService.writingDataToFile(reportData, INVALID_FILE_PATH));
    }
}
