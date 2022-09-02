package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    @Test
    public void report_correctData_ok() {
        Map<Fruit, Integer> data = Storage.dataBase;
        data.put(new Fruit("banana"), 107);
        data.put(new Fruit("apple"), 110);
        ReportService reportService = new ReportServiceImpl();
        String actual = reportService.report();
        System.out.println(actual);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,107" + System.lineSeparator()
                + "apple,110" + System.lineSeparator();
        System.out.println(expected);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.dataBase.clear();
    }
}
