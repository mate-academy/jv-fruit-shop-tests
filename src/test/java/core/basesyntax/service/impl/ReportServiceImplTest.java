package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String OUTPUT_TITLE
            = "fruit,quantity";
    private static Map<String, Integer> storage;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        storage = new HashMap<>();
        reportService = new ReportServiceImpl(new FruitDaoImpl());
    }

    @Test
    public void createReport_Empty_Ok() {
        List<String> reportLines = reportService.createReport();
        Assert.assertEquals(1, reportLines.size());
        Assert.assertEquals("Header: ", reportLines.get(0), OUTPUT_TITLE);
    }

    @Test
    public void createReport_Ok() {
        storage.put("apple", 5);
        storage.put("banana", 10);
        storage.put("mango", 15);
        List<String> list = reportService.createReport();
        for (Map.Entry<String, Integer> entry : storage.entrySet()) {
            list.add(entry.getKey() + "," + entry.getValue());
        }
        Assert.assertEquals("Size list: ", storage.size() + 1, list.size());
    }

    @After
    public void clean() {
        storage.clear();
    }
}
