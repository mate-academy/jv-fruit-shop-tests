package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.storage.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
public void createReport_Ok() {
        Storage.storage.put(new Fruit("banana"), 52);
        Storage.storage.put(new Fruit("apple"), 9);
        String expected = "fruit,quantity \n"
                + "banana,52\n"
                + "apple,9\n";
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }
}
