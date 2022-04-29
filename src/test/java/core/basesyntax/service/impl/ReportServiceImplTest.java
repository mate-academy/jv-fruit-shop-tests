package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
    }

    @Test
    public void createReport_withValidInput_Ok() {
        Storage.storage.put(new Fruit("apple"), 34);
        Storage.storage.put(new Fruit("banana"), 28);
        StringBuilder expected = new StringBuilder("fruit,quantity");
        expected
                .append(System.lineSeparator())
                .append("banana,28")
                .append(System.lineSeparator())
                .append("apple,34");

        String actual = reportService.createReport();
        Assert.assertEquals(expected.toString(), actual);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
