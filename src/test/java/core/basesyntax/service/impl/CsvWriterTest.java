package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvWriterTest {
    private static final String VALID_PATH_TO_REPORT_FILE =
            "src/test/resources/testFiles/report.csv";
    private static final String INVALID_PATH_TO_REPORT_FILE =
            "src/test/resources/testFiles/reports/report.csv";
    private static final String EXPECTED_VALID_PATH_TO_REPORT_FILE =
            "src/test/resources/testFiles/reportExpected.csv";
    private static final String VALID_REPORT = """
            fruit,quantity
            banana,152
            apple,90
            watermelon,10""";
    private static Writer csvWriter;
    private static Reader csvReader;

    @BeforeAll
    static void beforeAll() {
        csvWriter = new CsvWriter();
        csvReader = new CsvReader();
    }

    @Test
    void write_validData_ok() {
        csvWriter.write(VALID_PATH_TO_REPORT_FILE, VALID_REPORT);
        String actual = csvReader.readFile(VALID_PATH_TO_REPORT_FILE)
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(VALID_REPORT, actual);
    }

    @Test
    void write_invalidPathToReportFile_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvWriter.write(INVALID_PATH_TO_REPORT_FILE, VALID_REPORT);
        });
        String expected = "Cannot write file: " + INVALID_PATH_TO_REPORT_FILE;
        assertEquals(expected, runtimeException.getMessage());
    }

    @Test
    void write_pathIsNull_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvWriter.write(null, VALID_REPORT);
        });
        String expected = "Path must not be null.";
        assertEquals(expected, runtimeException.getMessage());
    }

    @Test
    void write_dataIsNull_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvWriter.write(VALID_PATH_TO_REPORT_FILE, null);
        });
        String expected = "Data must not be null.";
        assertEquals(expected, runtimeException.getMessage());
    }

    @Test
    void write_dataIsEmpty_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvWriter.write(VALID_PATH_TO_REPORT_FILE, "");
        });
        String expected = "Data must not be empty.";
        assertEquals(expected, runtimeException.getMessage());
    }
}
