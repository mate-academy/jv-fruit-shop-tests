package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,20"
            + System.lineSeparator()
            + "apple,100";
    private static ReportServiceImpl reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void report_correctHeader_ok() {
        String report = reportService.createReport();
        String[] splitReport = report.split("\n");
        assertEquals(HEADER, splitReport[0].trim());
    }

    @Test
    public void report_correctReport_ok() {
        Storage.storage.put(new Fruit("banana"), 20);
        Storage.storage.put(new Fruit("apple"), 100);
        String reportActual = reportService.createReport();
        assertEquals(REPORT, reportActual.trim());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
