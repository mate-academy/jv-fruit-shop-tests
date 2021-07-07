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
    private static final String YABLOKO = "yabloko";
    private static final String BANANCHIK = "bananchik";
    private static final String SEPARATOR = System.lineSeparator();
    private static Fruit yabloko;
    private static Fruit bananchik;
    private static FruitReportService fruitReportService;

    @BeforeClass
    public static void beforeClass() {
        yabloko = new Fruit(YABLOKO);
        bananchik = new Fruit(BANANCHIK);
        fruitReportService = new FruitReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.put(yabloko, 125);
        Storage.fruitStorage.put(bananchik, 521);
    }

    @Test
    public void report_getReport_Ok() {
        String expected = "fruit,quantity"
                + SEPARATOR
                + "bananchik,521"
                + SEPARATOR
                + "yabloko,125";

        List<String> expectedList = Arrays.asList(expected.split(SEPARATOR));
        List<String> actualList = Arrays.asList(fruitReportService.returnReport().split(SEPARATOR));
        Assert.assertEquals(expectedList, actualList);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

}
