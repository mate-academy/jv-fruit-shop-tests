package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final String INFO_STRING = "fruit,quantity";
    private static final String FIRST_FRUIT_INFO = "apple,50";
    private static final String SECOND_FRUIT_INFO = "pineapple,60";
    private static final String THIRD_FRUIT_INFO = "orange,70";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void crateReport_nullFruitMapInput_notOk() {
        Map<Fruit, Integer> nullFruitMap = new HashMap<>();
        nullFruitMap.put(null, 50);
        reportService.createReport(nullFruitMap);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullAmountMapInput_notOk() {
        Map<Fruit, Integer> nullIntegerMap = new HashMap<>();
        nullIntegerMap.put(new Fruit("apple"), null);
        reportService.createReport(nullIntegerMap);
    }

    @Test(expected = RuntimeException.class)
    public void crateReport_nullInput_notOk() {
        reportService.createReport(null);
    }

    @Test
    public void createReport_correctInputMap_ok() {
        Map<Fruit, Integer> correctFruitMap = new HashMap<>();
        correctFruitMap.put(new Fruit("apple"), 50);
        correctFruitMap.put(new Fruit("pineapple"), 60);
        correctFruitMap.put(new Fruit("orange"), 70);
        String actual = reportService.createReport(correctFruitMap);
        Assert.assertTrue("Report not contain needed data", actual.contains(INFO_STRING)
                && actual.contains(FIRST_FRUIT_INFO)
                && actual.contains(SECOND_FRUIT_INFO)
                && actual.contains(THIRD_FRUIT_INFO));
    }
}
