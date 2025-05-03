package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportCreatorCsvImplTest {
    private static final String DIVIDER = ",";

    @Test
    public void createReport_Ok() {
        ReportCreator reportCreator = new ReportCreatorCsvImpl();
        Map<Fruit, Integer> fruits = new HashMap<>();
        fruits.put(new Fruit("banana"), 100);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana" + DIVIDER
                + 100 + System.lineSeparator();
        String actual = reportCreator.createReport(fruits);
        assertEquals(expected, actual);
    }
}
