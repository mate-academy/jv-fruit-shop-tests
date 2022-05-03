package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportServiceImplTest {
    List<String> testReportData = List.of("fruit,quantity", "banana,100", "apple,50");
    List<String> testReportEmpty = List.of("fruit,quantity");
    ReportServiceImpl reportService;

    @Before
    public void setUp() throws Exception {
        FruitDao fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 50);
    }


    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void generatedReport_OK() {
        List<String> lines = reportService.generatedReport();

        assertEquals(testReportData, lines);
    }

    @Test
    public void generatedEmptyReport_Ok() {
        Storage.fruits.clear();
        List<String> lines = reportService.generatedReport();
        assertEquals(testReportEmpty, lines);
    }
}