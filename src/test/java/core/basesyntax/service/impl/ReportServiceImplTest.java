package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private ReportServiceImpl reportService = new ReportServiceImpl();

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void reportCreated_OK() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String actualReport = reportService.createReport();
        Assert.assertEquals(EXPECTED_REPORT, actualReport);
    }
}
