package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorServiceTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static ReportGeneratorService reportGeneratorService;

    @BeforeAll
    static void beforeAll() {
        reportGeneratorService = new ReportGeneratorServiceImpl(fruitDao);
    }

    @AfterEach
    void tearDown() {
        fruitDao.getStorage().clear();
    }

    @Test
    void createReport_Ok() {
        fruitDao.add("banana", 20);
        fruitDao.add("apple", 50);
        String report = reportGeneratorService.getReport();
        assertTrue(report.contains("banana,20"));
        assertTrue(report.contains("apple,50"));
    }

    @Test
    void createReport_ContainFirstLine() {
        String report = reportGeneratorService.getReport();
        assertTrue(report.contains("fruit,quantity"));
    }
}
