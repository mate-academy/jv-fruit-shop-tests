package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
    private static String expected;

    @BeforeClass
    public static void beforeClass() {
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator();
    }

    @Test
    public void createReport_validReport_ok() {
        Storage.fruits.put("banana", 100);
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_notOk() {
        String actual = reportCreatorService.createReport();
        assertNotEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
