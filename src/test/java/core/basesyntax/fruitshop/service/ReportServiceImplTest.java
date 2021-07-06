package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static Fruit apple;
    private static Fruit banana;
    private static FruitOperationDto fruitOperationDto;
    private static ReportService reportService;
    private static Map<Fruit, BigDecimal> storage;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        storage = new HashMap<>();
        reportService = new ReportServiceImpl();
    }

    @Test
    public void generateReport_NotEmptyStorageReport_isOk() {
        storage.put(apple, new BigDecimal(10));
        storage.put(banana, new BigDecimal(20));
        String expected = "fruit,quantity\r\nbanana,20\r\napple,10\r\n";
        String actual = reportService.generateReport(storage.entrySet());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_EmptyStorageReport_isOk() {
        String expected = "fruit,quantity\r\n";
        String actual = reportService.generateReport(new HashMap<Fruit, BigDecimal>().entrySet());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parse_NullReportException_isThrown() {
        reportService.generateReport(null);
    }
}
