package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String VALID_REPORT = "banana, 20" + System.lineSeparator()
            + "apple, 100";
    private ReportService reportService;
    private Map<String, Integer> data;

    @BeforeEach
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void test_Generate_Report_DataIsNull_NotOk() {
        data = null;
        assertThrows(NullPointerException.class, () -> reportService.generateReport(data));
    }

    @Test
    public void test_Generate_Report_Ok() {
        data = new HashMap<>();
        data.put("banana", 20);
        data.put("apple", 100);
        assertEquals(VALID_REPORT, reportService.generateReport(data));
    }
}
