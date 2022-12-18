package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getSameReport_Ok() {
        String expectedReport = new StringBuilder().append("banana,152")
                .append(System.lineSeparator()).append("apple,90").append(System.lineSeparator())
                .toString();
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        assertEquals(expectedReport,reportService.makeReport());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
