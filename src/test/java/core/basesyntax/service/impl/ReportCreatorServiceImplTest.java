package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
    private static String expected;

    @Test
    public void createReport_validReport_ok() {
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator();
        Storage.fruits.put("banana", 100);
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
