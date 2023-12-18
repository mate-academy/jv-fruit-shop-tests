package core.basesyntax.service;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;

    @Test
    void textMassage_Null() {
        reportService = new ReportService();
        Storage.remnantsOfGoods = new HashMap<>();
        String report = reportService.getReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(expectedReport, report);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.remnantsOfGoods.clear();
    }
}
