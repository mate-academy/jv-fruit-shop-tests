package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFormatDataProcessingServiceImpl;
import core.basesyntax.service.impl.CsvReportServiceImpl;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportingServiceTest {
    private static ReportingService reportingService;
    private static List<String> reportList;
    private static DataProcessingService processingService;

    @BeforeClass
    public static void setUp() {
        reportList = InitialisationService.getReportList();
        processingService = new CsvFormatDataProcessingServiceImpl(
                InitialisationService.getFruitDaoStrategyMap());
        reportingService = new CsvReportServiceImpl(reportList.get(0));
        processingService.processingData(InitialisationService.getDataList());
    }

    @Test
    public void generateReport_ok() {
        Assert.assertEquals(reportList, reportingService.generateReport());
    }

    @AfterClass
    public static void clearStorage() {
        InitialisationService.clearStorage();
    }
}
