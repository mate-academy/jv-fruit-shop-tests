package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static String expected;
    private static ReportService report;

    @BeforeClass
        public static void beforeClass() {
        Storage.getDataBase().put(new Fruit("banana"), 10);
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator();
        report = new ReportServiceImpl();
    }

    @Test
    public void getReportFromDataBase_ok() {
        Assert.assertEquals(expected, report.getReport());
    }

    @AfterClass
    public static void afterClass() {
        Storage.getDataBase().clear();
    }
}
