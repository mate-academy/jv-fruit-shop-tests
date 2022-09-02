package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void reportCreate_OK() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator() + "apple,14";
        Storage.storage.put(new Fruit("banana"), 25);
        Storage.storage.put(new Fruit("apple"), 14);
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportCreate_emptyStorage_OK() {
        String expected = "fruit,quantity";
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
