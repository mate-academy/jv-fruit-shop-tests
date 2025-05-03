package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.CsvReportServiceImpl;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportingServiceTest {
    private static final int EXPECTED_BANANA_AMOUNT = 162;
    private static final int EXPECTED_APPLE_AMOUNT = 80;
    private static ReportingService reportingService;
    private static List<String> reportList;

    @BeforeClass
    public static void setUp() {
        reportList = InitialisationService.getReportList();
        reportingService = new CsvReportServiceImpl(reportList.get(0));
    }

    @Test
    public void generateReport_ok() {
        Storage.storage.put(new Fruit("banana"), EXPECTED_BANANA_AMOUNT);
        Storage.storage.put(new Fruit("apple"), EXPECTED_APPLE_AMOUNT);
        Assert.assertEquals(reportList, reportingService.generateReport());
    }

    @AfterClass
    public static void clearStorage() {
        InitialisationService.clearStorage();
    }
}
