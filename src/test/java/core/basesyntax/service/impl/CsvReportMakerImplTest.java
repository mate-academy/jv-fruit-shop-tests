package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportMaker;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReportMakerImplTest {

    private static ReportMaker reportMaker;
    private static Map<Fruit, Integer> batch;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        reportMaker = new CsvReportMakerImpl();
        report = "fruit,quantity" + System.lineSeparator()
            + "banana,125" + System.lineSeparator()
            + "apple,63";
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        batch = Map.of(apple, 63, banana, 125);
    }

    @Test
    public void makeReport_validBatch_Ok() {
        String expected = report;
        String actual = reportMaker.makeReport(batch);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makeReport_emptyBatch_Ok() {
        String expected = "fruit,quantity";
        String actual = reportMaker.makeReport(new HashMap<>());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void makeReport_nullBatch_notOk() {
        reportMaker.makeReport(null);
    }
}