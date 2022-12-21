package core.basesyntax.service;

import static org.hamcrest.CoreMatchers.is;

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
    private static Map<Fruit, Integer> nullFruitMap;
    private static Map<Fruit, Integer> nullIntegerMap;
    private static Map<Fruit, Integer> correctFruitMap;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        nullFruitMap = new HashMap<>();
        nullIntegerMap = new HashMap<>();
        nullFruitMap.put(null, 50);
        nullIntegerMap.put(new Fruit("apple"), null);
        correctFruitMap = new HashMap<>();
        correctFruitMap.put(new Fruit("apple"), 50);
        correctFruitMap.put(new Fruit("pineapple"), 60);
        correctFruitMap.put(new Fruit("orange"), 70);
    }

    @Test
    public void createReport_nullInputValues_notOk() {
        try {
            reportService.createReport(null);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong input data"));
        }
        try {
            reportService.createReport(nullFruitMap);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong input data"));
        }
        try {
            reportService.createReport(nullIntegerMap);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong input data"));
        }
    }

    @Test
    public void createReport_correctInputMap_ok() {
        String actual = reportService.createReport(correctFruitMap);
        System.out.println(actual);
        Assert.assertTrue("Report not contain needed data", actual.contains(INFO_STRING)
                && actual.contains(FIRST_FRUIT_INFO)
                && actual.contains(SECOND_FRUIT_INFO)
                && actual.contains(THIRD_FRUIT_INFO));
    }
}
