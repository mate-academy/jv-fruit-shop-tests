package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.ReportGeneratorException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorCsvImplTest {
    private static ReportGeneratorCsvImpl reportGenerator;
    private static Map<String, Integer> reportData;
    private static final String REPORT_TITLE = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FIELD_SEPARATOR = ",";
    private static final int TITLE_ROW_INDEX = 0;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorCsvImpl();
        reportData = new HashMap<>();
    }

    @BeforeEach
    void beforeEach() {
        reportData.clear();
    }

    @Test
    public void create_nullReportData_notOk() {
        assertThrows(ReportGeneratorException.class, () -> reportGenerator.create(null));
    }

    @Test
    public void create_emptyReportData_notOk() {
        assertThrows(ReportGeneratorException.class, () -> reportGenerator.create(reportData));
    }

    @Test
    public void create_nullKeyReportData_notOk() {
        reportData.put(null, 10);
        assertThrows(ReportGeneratorException.class, () -> reportGenerator.create(reportData));
    }

    @Test
    public void create_emptyKeyReportData_notOk() {
        reportData.put("", 10);
        assertThrows(ReportGeneratorException.class, () -> reportGenerator.create(reportData));
    }

    @Test
    public void create_nullValueReportData_notOk() {
        reportData.put("apple", null);
        assertThrows(ReportGeneratorException.class, () -> reportGenerator.create(reportData));
    }

    @Test
    public void create_negativeValueReportData_notOk() {
        reportData.put("apple", -12);
        assertThrows(ReportGeneratorException.class, () -> reportGenerator.create(reportData));
    }

    @Test
    public void create_emptyReport_notOk() {
        reportData.put("apple", 12);
        String report = reportGenerator.create(reportData);
        assertFalse(report.isEmpty());
    }

    @Test
    public void create_wrongTitleReport_notOk() {
        reportData.put("apple", 12);
        String report = reportGenerator.create(reportData);
        String[] reportRows = report.split(LINE_SEPARATOR);
        assertEquals(REPORT_TITLE, reportRows[TITLE_ROW_INDEX]);
    }

    @Test
    public void create_withoutProductDataReport_notOk() {
        reportData.put("apple", 12);
        reportData.put("banana", 12);
        String report = reportGenerator.create(reportData);
        String[] reportRows = report.split(LINE_SEPARATOR);
        assertTrue(reportRows.length > 1);
    }

    @Test
    public void create_threeProductReport_ok() {
        reportData.put("apple", 67);
        reportData.put("banana", 49);
        reportData.put("tangerine", 122);

        String actual = reportGenerator.create(reportData);
        String expected = REPORT_TITLE + LINE_SEPARATOR
                + "banana" + FIELD_SEPARATOR + 49 + LINE_SEPARATOR
                + "apple" + FIELD_SEPARATOR + 67 + LINE_SEPARATOR
                + "tangerine" + FIELD_SEPARATOR + 122 + LINE_SEPARATOR;

        assertEquals(expected, actual);
    }

    @Test
    public void create_oneProductReport_ok() {
        reportData.put("apple", 67);

        String actual = reportGenerator.create(reportData);
        String expected = REPORT_TITLE + LINE_SEPARATOR
                + "apple" + FIELD_SEPARATOR + 67 + LINE_SEPARATOR;

        assertEquals(expected, actual);
    }

    @Test
    public void create_fiveProductReport_ok() {
        reportData.put("apple", 67);
        reportData.put("banana", 49);
        reportData.put("tangerine", 122);
        reportData.put("orange", 55);
        reportData.put("lemon", 82);

        String actual = reportGenerator.create(reportData);
        String expected = REPORT_TITLE + LINE_SEPARATOR
                + "banana" + FIELD_SEPARATOR + 49 + LINE_SEPARATOR
                + "orange" + FIELD_SEPARATOR + 55 + LINE_SEPARATOR
                + "apple" + FIELD_SEPARATOR + 67 + LINE_SEPARATOR
                + "lemon" + FIELD_SEPARATOR + 82 + LINE_SEPARATOR
                + "tangerine" + FIELD_SEPARATOR + 122 + LINE_SEPARATOR;

        assertEquals(expected, actual);
    }
}
