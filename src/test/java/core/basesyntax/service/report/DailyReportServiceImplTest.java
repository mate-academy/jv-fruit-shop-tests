package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class DailyReportServiceImplTest {
    private static DailyReportService dailyReportService;
    private String expectedReport;
    private String actualReport;

    @BeforeClass
    public static void beforeClass() {
        FruitStorage.fruitsDataBase.put(new Fruit("banana"), 100);
        FruitStorage.fruitsDataBase.put(new Fruit("apple"), 200);
        dailyReportService = new DailyReportServiceImpl();
    }

    @Test
    public void createReport_ok() {
        expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,200" + System.lineSeparator();
        actualReport = dailyReportService.createReport();
        FruitStorage.fruitsDataBase.clear();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReport_emptyData_ok() {
        FruitStorage.fruitsDataBase.clear();
        expectedReport = "fruit,quantity" + System.lineSeparator();
        actualReport = dailyReportService.createReport();
        assertEquals(expectedReport, actualReport);
    }
}
