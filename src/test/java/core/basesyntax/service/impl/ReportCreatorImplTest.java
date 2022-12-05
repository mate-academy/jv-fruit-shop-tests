package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreator;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;



public class ReportCreatorImplTest {
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @Test
    public void createReport_CorrectDate_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,200" + System.lineSeparator();
        Map<String, Integer> map = new HashMap<>();
        map.put("banana", 100);
        map.put("apple", 200);
        String actual = reportCreator.createReport(map);
        Assert.assertEquals(expected, actual);
    }
}
