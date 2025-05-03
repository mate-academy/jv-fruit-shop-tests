package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.CreateReport;
import org.junit.Assert;
import org.junit.Test;

public class CreateReportImplTest {
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,500"
            + System.lineSeparator();
    private CreateReport createReport = new CreateReportImpl();

    @Test
    public void createReport_Ok() {
        Storage.storageFruits.clear();
        Storage.getStorage().put("banana", 500);
        String expected = REPORT;
        String actual = createReport.getReport();
        Assert.assertEquals(expected, actual);
    }
}
