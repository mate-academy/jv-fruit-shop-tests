package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";

    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        Storage.fruitsStorage.put("banana", 152);
        Storage.fruitsStorage.put("apple", 90);
        String actualReport = reportService.createReport();
        assertEquals(EXPECTED_REPORT, actualReport);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
