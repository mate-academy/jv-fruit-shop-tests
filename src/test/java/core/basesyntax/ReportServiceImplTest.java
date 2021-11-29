package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String REPORT_CAPTION = "fruit,quantity";
    private static ReportServiceImpl reportService;

    @BeforeClass
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_correctData_ok() {
        Storage.reportMap.put(new Fruit("apple"), 10);
        Storage.reportMap.put(new Fruit("banana"), 10);
        String expected = REPORT_CAPTION + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        String actual = reportService.getReport();
        Assert.assertEquals(expected,actual);
    }

    @After
    public void afterEachTest() {
        Storage.reportMap.clear();
    }
}

