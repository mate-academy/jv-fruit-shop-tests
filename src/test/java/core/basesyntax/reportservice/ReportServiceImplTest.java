package core.basesyntax.reportservice;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService;

    @Before
    public void initializationOfVariables() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_checkWork_ok() {
        Storage.put("banana", 30);
        Storage.put("apple", 50);
        Storage.put("orange", 10);

        String expected = "banana,30" + "\n"
                + "orange,10" + "\n"
                + "apple,50" + "\n";
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
        Storage.getStorage().clear();
    }

    @Test
    public void createReport_storageIsEmpty_notOk() {
        String actual = reportService.createReport();
        String expected = "";
        Assert.assertEquals(expected, actual);
    }
}
