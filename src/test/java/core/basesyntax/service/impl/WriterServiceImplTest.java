package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    public static final String PATH_TO_REPORT_FILE_TEST =
            "src/test/java/resources/testValidOutput.csv";
    public static final String WRONG_PATH_TO_FILE =
            "src/test/resources/noExistingFile.csv";
    public static final String report =
            "fruit,quantity "
                    + "banana,152 "
                    + "apple,120";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeReportToFileWriterServiceWrongPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeReportToFile(WRONG_PATH_TO_FILE, report));

    }

    @Test
    void writeReportToFileWriterServiceValidPath_Of() {
        writerService.writeReportToFile(PATH_TO_REPORT_FILE_TEST, report);
        String actualReport = readDataFromFile(PATH_TO_REPORT_FILE_TEST);
        assertEquals(report, actualReport);
    }

    private String readDataFromFile(String path) {
        try {
            List<String> strList = Files.readAllLines(Path.of(path));
            return strList.stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
