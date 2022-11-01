package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @BeforeClass
    public static void init() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setFruitStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void createReport_correctStorage_ok() {
        FruitStorage.storage.put(new Fruit("banana"), 152);
        FruitStorage.storage.put(new Fruit("apple"), 50);
        StringBuilder stringBuilder = new StringBuilder(FIRST_LINE);
        stringBuilder.append(LINE_SEPARATOR).append("banana,152");
        stringBuilder.append(LINE_SEPARATOR).append("apple,50");
        Assert.assertEquals(stringBuilder.toString(), reportService.createReport());
    }

    @Test
    public void createReport_emptyStorage_ok() {
        Assert.assertEquals(FIRST_LINE, reportService.createReport());
    }

    @Test
    public void createReport_WithoutFirstLine_notOk() {
        FruitStorage.storage.put(new Fruit("banana"), 152);
        Assert.assertNotEquals(LINE_SEPARATOR + "banana,152", reportService.createReport());
    }
}
