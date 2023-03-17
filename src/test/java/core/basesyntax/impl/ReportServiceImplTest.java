package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final Map<String, Integer> EMPTY_MAP = new HashMap<>();
    private static Map<String, Integer> VALID_MAP = new HashMap<>();
    private static final Map<String, Integer> NULL_MAP = null;
    private static ReportService reportService;
    private static final String EXPECTED_REPORT =
            "fruit,quantity" + System.lineSeparator()
            + "banana,12" + System.lineSeparator()
            + "apple,221";

    @Before
    public void beforeAll() {
        reportService = new ReportServiceImpl();
        VALID_MAP.put("banana", 12);
        VALID_MAP.put("apple", 221);
    }

    @Test
    public void realizePattern_storageValid_ok() {
        String actual = reportService.createReport(VALID_MAP);
        assertEquals(EXPECTED_REPORT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void realizePattern_storageEmpty_notOk() {
        reportService.createReport(EMPTY_MAP);
    }

    @Test(expected = RuntimeException.class)
    public void realizePattern_storageNull_notOk() {
        reportService.createReport(NULL_MAP);
    }
}
