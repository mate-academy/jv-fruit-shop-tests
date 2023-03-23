package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @Before
    public void setUp() throws Exception {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void createReport_Ok() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String expectedReport = "fruit,quantity" + System.lineSeparator() + "banana,152" +
                System.lineSeparator() + "apple,90";
        String actualReport = reportCreator.createReport(Storage.fruits);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportCreator.createReport(Storage.fruits);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReport_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> reportCreator.createReport(null));
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
