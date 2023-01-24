package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitReport;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @Before
    public void cleanBefore() {
        Storage.fruits.clear();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void reportServiceImpl_createReport_Ok() {
        String expected = "fruit,quantity\r\n"
                + "banana,2700\r\n"
                + "apple,300";

        List<FruitReport> actualList = new ArrayList<>();
        actualList.add(new FruitReport("banana",2700));
        actualList.add(new FruitReport("apple", 300));

        String actual = reportService.createReport(actualList);
        Assert.assertEquals(actual, expected);
    }
}
