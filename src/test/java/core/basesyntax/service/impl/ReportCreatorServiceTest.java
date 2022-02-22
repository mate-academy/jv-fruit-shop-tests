package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final String SEPARATOR = ",";
    private static final ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();

    @Before
    public void setUp() {
        Storage.fruitBalance.clear();
    }

    @Test
    public void createReport_correctData_ok() {
        Storage.fruitBalance.put(new Fruit("cherry"), 100);
        Storage.fruitBalance.put(new Fruit("apple"), 100);
        Storage.fruitBalance.put(new Fruit("banana"), 100);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "cherry" + SEPARATOR + 100 + System.lineSeparator()
                + "banana" + SEPARATOR + 100 + System.lineSeparator()
                + "apple" + SEPARATOR + 100;
        String actualReport = reportCreatorService.createReport();
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test(expected = DataProcessingException.class)
    public void createReport_emptyStorage_notOk() {
        reportCreatorService.createReport();
    }
}
