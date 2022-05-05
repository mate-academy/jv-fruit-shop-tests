package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private List<String> testReportData;
    private ReportServiceImpl reportService;

    @Before
    public void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 50);
        testReportData = List.of("fruit,quantity", "banana,100", "apple,50");
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void generatedReport_OK() {
        List<String> actual = reportService.generatedReport();
        assertEquals(testReportData, actual);
    }

    @Test
    public void generatedEmptyReport_Ok() {
        Storage.fruits.clear();
        List<String> actual = reportService.generatedReport();
        assertEquals(Collections.emptyList(), actual);
    }
}
