package service;

import static org.junit.Assert.assertEquals;

import dao.FruitDaoImpl;
import db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.ReportServiceImpl;

class ReportServiceImplTest {
    private static final ReportService reportService 
            = new ReportServiceImpl(new FruitDaoImpl());

    @BeforeEach
    void clearFruitHashMap() {
        Storage.fruitHashMap.clear();
    }

    @Test
    void createReport_Ok() {
        Storage.fruitHashMap.put("banana", 100);
        String actual = reportService.createReport();
        String expected = System.lineSeparator() + "banana,100";
        assertEquals(expected, actual);
    }
}
