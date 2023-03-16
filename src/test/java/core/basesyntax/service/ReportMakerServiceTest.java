package core.basesyntax.service;

import core.basesyntax.service.impl.ReportMakerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ReportMakerServiceTest {
    @Test
    public void reporterMaker_report_ok() {
        Map<String, Integer> base = new HashMap<>();
        base.put("banana", 5);
        base.put("apple", 25);
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana, 5" + System.lineSeparator()
                + "apple, 25";
        String actual = new ReportMakerServiceImpl().report(base);
        Assert.assertEquals(expected, actual);
    }
}
