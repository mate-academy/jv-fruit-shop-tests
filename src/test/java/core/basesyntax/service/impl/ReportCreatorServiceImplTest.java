package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private ReportCreatorService reportCreatorService;
    private String expected;

    @Before
    public void setUp() throws Exception {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void reportCreate_OK() {
        expected = "fruit,quantity\nbanana,25\napple,14";
        Storage.storage.put(new Fruit("banana"), 25);
        Storage.storage.put(new Fruit("apple"), 14);
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }
    //
    //    @Test
    //    public void emptyMap_ReportCreate_OK() {
    //        expected = "fruit,quantity";
    //        String actual = reportCreatorService.createReport();
    //        assertEquals(expected, actual);
    //    }

    @Test
    public void nullValues_ReportCreate_OK() {
        expected = "fruit,quantity\nnull,null\nnull,null";
        Storage.storage.put(new Fruit(null), null);
        Storage.storage.put(null, null);
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
