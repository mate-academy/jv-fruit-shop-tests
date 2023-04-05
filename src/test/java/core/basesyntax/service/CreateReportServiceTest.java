package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.CreateReportImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportServiceTest {
    private static final String HEADER_FRUIT = "fruitTransaction,";
    private static final String HEADER_QUANTITY = "quantity";
    private static final String BANANA_REPORT = "banana 152";
    private static final String APPLE_REPORT = "apple 90";
    private static CreateReportService createReport;

    @BeforeClass
    public static void beforeClass() {
        createReport = new CreateReportImpl();
    }

    @Test
    public void createReport_validData_ok() {
        String expectedReportString = expectedReport();
        fillStorage();
        String actualReportString = createReport.generateReport();

        Assert.assertEquals(expectedReportString, actualReportString);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expectedReportString = "fruitTransaction,quantity";
        String actualReportString = createReport.generateReport();

        Assert.assertEquals(expectedReportString, actualReportString);
    }

    private void fillStorage() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
    }

    private String expectedReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER_FRUIT).append(HEADER_QUANTITY).append(System.lineSeparator())
                .append(BANANA_REPORT).append(System.lineSeparator())
                .append(APPLE_REPORT);
        return stringBuilder.toString();
    }
}
