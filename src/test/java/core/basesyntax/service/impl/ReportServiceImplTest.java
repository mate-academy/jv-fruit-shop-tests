package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReportServiceImplTest {
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_ok() {
        Storage.fruitStorage.put("banana", 152);
        String expected = "fruit,quantity" +
                System.lineSeparator() +
                "banana,152" +
                System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}