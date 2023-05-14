package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    public static final int BANANA_QUANTITY = 15;
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_ok() {
        Storage.fruitStorage.put("banana", BANANA_QUANTITY);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,15"
                + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
