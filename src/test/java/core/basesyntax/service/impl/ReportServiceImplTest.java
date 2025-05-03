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
    private String expected;
    private List<FruitReport> actualList = new ArrayList<>();

    @Before
    public void prepareBefore() {
        actualList.add(new FruitReport("banana",2700));
        actualList.add(new FruitReport("apple", 300));
        StringBuilder builder = new StringBuilder();
        expected = builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,2700")
                .append(System.lineSeparator())
                .append("apple,300").toString();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void reportServiceImpl_createReport_Ok() {
        String actual = reportService.createReport(actualList);
        Assert.assertEquals(actual, expected);
    }
}
