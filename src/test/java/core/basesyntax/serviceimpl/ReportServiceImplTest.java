package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static final String CORRECT_HEADER = "fruit,quantity";
    private static final int HEADER_LINE_INDEX = 0;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EXPECTED_REPORT = "fruit,quantity" + LINE_SEPARATOR
            + "banana,152" + LINE_SEPARATOR
            + "apple,90";
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        Storage.fruits.clear();
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
    }

    @Test
    void createReport_ValidData_Ok() {
        String actualReport = reportService.generateReport(Storage.fruits);
        assertEquals(EXPECTED_REPORT, actualReport);
    }

    @Test
    void createReport_HeaderInReport_Ok() {
        String[] lines = reportService.generateReport(Storage.fruits).split(LINE_SEPARATOR);
        String expected = CORRECT_HEADER;
        String actual = lines[HEADER_LINE_INDEX];
        assertEquals(expected, actual);
    }
}
