package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void createReport_fullStorage_ok() {
        FruitStorage.storageFruits.put("banana", 152);
        FruitStorage.storageFruits.put("apple", 90);
        String expected = reportCreatorService.createReport();
        String actual = "fruit,quantity" + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expected = reportCreatorService.createReport();
        String actual = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }
}
