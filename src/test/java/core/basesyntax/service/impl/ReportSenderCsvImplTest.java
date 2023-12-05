package core.basesyntax.service.impl;

import core.basesyntax.exceptions.ReportSenderException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportSenderCsvImplTest {
    private static String TEST_REPORT_CSV_FILE_PATH =
        "src/test/java/resources/testReport.csv";
    private static final String TITLE_ROW = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FIELD_SEPARATOR = ",";
    private static ReportSenderCsvImpl reportSenderCsv;
    private static ReportGeneratorCsvImpl reportGeneratorCsv;

    @BeforeAll
    static void beforeAll() {
        reportSenderCsv = new ReportSenderCsvImpl(TEST_REPORT_CSV_FILE_PATH);
        reportGeneratorCsv = new ReportGeneratorCsvImpl();
    }

    @AfterEach
    public void clearTestReport() {
        try {
            Files.deleteIfExists((Path.of(TEST_REPORT_CSV_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    void send_nullReport_notOk() {
        assertThrows(ReportSenderException.class, () -> reportSenderCsv.send(null));
    }

    @Test
    void send_emptyReport_notOk() {
        assertThrows(ReportSenderException.class, () -> reportSenderCsv.send(""));
    }

    @Test
    void send_report_ok() {
        Map<String, Integer> reportData = new HashMap<>();
        reportData.put("banana", 152);
        reportData.put("apple", 90);

        String report = reportGeneratorCsv.create(reportData);
        reportSenderCsv.send(report);

        String actualResult = readFromFile(TEST_REPORT_CSV_FILE_PATH);

        String expectedResult = TITLE_ROW + LINE_SEPARATOR
            + "banana" + FIELD_SEPARATOR + "152" + LINE_SEPARATOR
            + "apple" + FIELD_SEPARATOR + "90" + LINE_SEPARATOR;

        assertEquals(expectedResult, actualResult);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }

}