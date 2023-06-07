package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.Report;
import core.basesyntax.service.impl.ReportImpl;
import org.junit.After;
import org.junit.Test;

public class ReportImplTest {

    @Test
    public void createReport_Ok() {
        Storage.storage.put("banana", 17);
        Storage.storage.put("apple", 24);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,17" + System.lineSeparator() + "apple,24";
        Report reportCreator = new ReportImpl();
        String actualReport = reportCreator.createReport(Storage.storage);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expectedReport = "fruit,quantity";
        Report report = new ReportImpl();
        String actualReport = report.createReport(Storage.storage);
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
