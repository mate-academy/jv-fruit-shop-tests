package core.basesyntax.service.impl;

import core.basesyntax.model.FruitCrate;
import core.basesyntax.service.ReportMaker;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerImplTest {
    private static ReportMaker reportMaker;
    private static List<FruitCrate> batch;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        reportMaker = new ReportMakerImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "apple,63" + System.lineSeparator()
                + "banana,125";
        batch = new ArrayList<>(List.of(new FruitCrate("apple", 63),
                new FruitCrate("banana", 125)));
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
        String actual = reportMaker.makeReport(new ArrayList<>());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void makeReport_nullBatch_notOk() {
        reportMaker.makeReport(null);
    }
}
