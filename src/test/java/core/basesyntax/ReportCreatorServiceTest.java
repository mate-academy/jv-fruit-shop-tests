package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreatorService;
    private static final String REPORT_CSV_HEADER = "fruit,quantity\n";

    @BeforeClass
    public static void initReportCreatorService() {
        reportCreatorService = new ReportCreatorServiceImpl(
                new FruitServiceImpl(
                        new FruitDaoImpl()
                ));
    }

    @BeforeClass
    public static void fillFruitsStorage() {
        Storage.fruitsMap.put("apple", 100);
        Storage.fruitsMap.put("banana", 20);
        Storage.fruitsMap.put("apricot", 54);
        Storage.fruitsMap.put("pineapple", 81);
    }

    @After
    public void clearFruitsStorage() {
        Storage.fruitsMap.clear();
    }

    @Test
    public void createReport_dataCorrectly_Ok() {
        String expectedReport = REPORT_CSV_HEADER
                + Storage.fruitsMap.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "," + entry.getValue())
                .collect(Collectors.joining("\n"));
        Assert.assertEquals(expectedReport, reportCreatorService.createReport());
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expectedReport = REPORT_CSV_HEADER;
        Assert.assertEquals(expectedReport, reportCreatorService.createReport());
    }
}
