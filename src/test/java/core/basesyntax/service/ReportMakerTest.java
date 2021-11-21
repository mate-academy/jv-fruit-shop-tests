package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportMakerImpl;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerTest {
    private static ReportMaker reportMaker;
    private static final List<Fruit> FRUITS =
            List.of(new Fruit("apple"),
                    new Fruit("apple"),
                    new Fruit("apple"),
                    new Fruit("apple"),
                    new Fruit("apple"),
                    new Fruit("banana"),
                    new Fruit("banana"));
    private static final String EXPECTED =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,2" + System.lineSeparator()
                    + "apple,5";

    @BeforeClass
    public static void beforeClass() {
        reportMaker = new ReportMakerImpl();
    }

    @Test
    public void makeReport_validData_ok() {
        String actual = reportMaker.makeReport(FRUITS);
        Assert.assertEquals(EXPECTED, actual);
    }

    @Test
    public void makeReport_emptyFruitsList_notOk() {
        String actual = reportMaker.makeReport(Collections.emptyList());
        String expected = "fruit,quantity";
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void makeReport_nullData_notOk() {
        reportMaker.makeReport(null);
    }
}
