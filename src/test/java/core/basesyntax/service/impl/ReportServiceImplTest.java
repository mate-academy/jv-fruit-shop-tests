package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportServiceImpl reportService;
    private HashMap<Fruit, Integer> fruitsDataMap;

    @Before
    public void setUp() throws Exception {
        reportService = new ReportServiceImpl();
        fruitsDataMap = new HashMap<>();
        fruitsDataMap.put(new Fruit("banana"), 88);
    }

    @Test
    public void reportCreateValid() {
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,88";
        String actual = reportService.create(fruitsDataMap.entrySet());
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void reportCreateNull() {
        Map<Fruit, Integer> fruitsDataMap = null;
        reportService.create(fruitsDataMap.entrySet());
    }
}
