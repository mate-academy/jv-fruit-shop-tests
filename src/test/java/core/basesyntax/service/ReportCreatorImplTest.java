package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorImplTest {

    @Test
    public void createReport_Ok() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        ReportCreator reportCreator = new ReportCreatorImpl();
        String actualReport = reportCreator.createReport(Storage.fruits);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expectedReport = "fruit,quantity";
        ReportCreator reportCreator = new ReportCreatorImpl();
        String actualReport = reportCreator.createReport(Storage.fruits);
        assertEquals(expectedReport, actualReport);
    }

    @Test(expected = NullPointerException.class)
    public void createReport_nullInput_notOk() {
        ReportCreator reportCreator = new ReportCreatorImpl();
        reportCreator.createReport(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
