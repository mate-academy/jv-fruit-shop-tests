package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.CreatReportService;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreatReportServiceImplTest {
    private static CreatReportService creatReportService;

    @BeforeClass
    public static void setUp() {
        creatReportService = new CreatReportServiceImpl();
    }

    @Test
    public void createReport_validData_Ok() {
        String expected = "fruit,quantity\nbanana,15\napple,100\n";
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("banana"), 15);
        String actual = creatReportService.createReport();
        assertEquals(expected, actual);
    }
}
