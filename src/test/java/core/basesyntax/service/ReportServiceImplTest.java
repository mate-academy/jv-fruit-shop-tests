package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void report_correctData_ok() {
        Map<Fruit, Integer> data = Storage.dataBase;
        data.put(new Fruit("banana"), 107);
        data.put(new Fruit("apple"), 110);
        String actual = reportService.report();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,107" + System.lineSeparator()
                + "apple,110" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.dataBase.clear();
    }
}
