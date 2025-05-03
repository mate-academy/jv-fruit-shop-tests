package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void createReport_validReport_ok() {
        String expected = "fruit,quantity" + LINE_SEPARATOR + "banana,100" + LINE_SEPARATOR;
        Storage.fruits.put("banana", 100);
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expected = "fruit,quantity" + LINE_SEPARATOR;
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
