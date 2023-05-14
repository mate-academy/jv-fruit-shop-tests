package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final String REGEX = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int BANANA_AMOUNT = 20;
    private static final int APPLE_AMOUNT = 100;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void createReport_mapIsNull_notOk() {
        reportService.createReport(null);
    }

    @Test
    public void createReport_successfulReport_ok() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        String expected = new StringBuilder().append(TITLE).append(LINE_SEPARATOR)
                .append(banana.getName()).append(REGEX).append(BANANA_AMOUNT).append(LINE_SEPARATOR)
                .append(apple.getName()).append(REGEX).append(APPLE_AMOUNT).append(LINE_SEPARATOR)
                .toString();
        Map<Fruit, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put(banana, BANANA_AMOUNT);
        fruitStorage.put(apple, APPLE_AMOUNT);
        String actual = reportService.createReport(fruitStorage);
        Assert.assertEquals(expected, actual);
    }
}
