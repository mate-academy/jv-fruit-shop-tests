package core.basesyntax.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitReportService;
import core.basesyntax.service.FruitReportServiceImpl;
import core.basesyntax.storage.Storage;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportServiceTest {
    private static final String APPLE = "apple";
    private static final String PINEAPPLE = "pineapple";
    private static final String SEPARATOR = System.lineSeparator();
    private static Fruit apple;
    private static Fruit pineapple;
    private static FruitReportService fruitReportService;

    @BeforeClass
    public static void beforeClass() {
        apple = new Fruit(APPLE);
        pineapple = new Fruit(PINEAPPLE);
        fruitReportService = new FruitReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.put(apple, 125);
        Storage.fruitStorage.put(pineapple, 521);
    }

    @Test
    public void report_getReport_Ok() {
        String expected = "fruit,quantity"
                + SEPARATOR
                + "pineapple,521"
                + SEPARATOR
                + "apple,125";

        List<String> expectedList = Arrays.asList(expected.split(SEPARATOR));
        List<String> actualList = Arrays.asList(fruitReportService.returnReport().split(SEPARATOR));
        Assert.assertEquals(expectedList, actualList);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

}
