package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.CreateReportImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportServiceTest {
    private static CreateReportService createReport;

    @BeforeClass
    public static void beforeClass() {
        createReport = new CreateReportImpl();
    }

    @Test
    public void createReport_validData_ok() {
        String expectedReportString = "fruit,quantity" + System.lineSeparator()
                + "banana 152" + System.lineSeparator()
                + "apple 90";
        fillStorage();
        String actualReportString = createReport.generateReport();

        Assert.assertEquals(expectedReportString, actualReportString);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expectedReportString = "fruit,quantity";
        String actualReportString = createReport.generateReport();

        Assert.assertEquals(expectedReportString, actualReportString);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    private void fillStorage() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
    }
}
