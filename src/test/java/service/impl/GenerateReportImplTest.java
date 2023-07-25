package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.GenerateReport;

class GenerateReportImplTest {

    private GenerateReport report;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        report = new GenerateReportImpl();
        fruitStorageDao = new FruitStorageDaoImpl();

    }

    @Test
    void generateReport_ok() {
        fruitStorageDao.set("banana", 100);
        fruitStorageDao.set("apple", 25);
        Map<String,Integer> input = fruitStorageDao.getFruits();

        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,25");

        assertEquals(expected, report.generateReport(input),
                "Actual and expected reports must be equals");
    }
}
