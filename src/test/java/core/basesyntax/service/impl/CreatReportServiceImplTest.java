package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.CreatReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreatReportServiceImplTest {
    private CreatReportService creatReportService;

    @Before
    public void setUp() throws Exception {
        creatReportService = new CreatReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        String expected = "fruit,quantity\nbanana,15\napple,100\n";
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("banana"), 15);
        String actual = creatReportService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
