package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportServiceImpl reportService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        reportService = new ReportServiceImpl("fruit,quantity", ":");
        reportService.generateReport();
    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitMap.clear();
    }

    @Test
    void generateReport_noFruits_ok() {
        List<String> expected = List.of("fruit,quantity");
        List<String> result = reportService.generateReport();
        assertEquals(expected, result);
    }

    @Test
    void generateReport_singleFruit_ok() {
        storageDao.addFruit("apple", 10);
        List<String> expected = List.of("fruit,quantity", "apple:10");
        List<String> result = reportService.generateReport();
        assertEquals(expected, result);
    }

    @Test
    void generateReport_multipleFruits_ok() {
        storageDao.addFruit("apple", 10);
        storageDao.addFruit("banana", 20);
        storageDao.addFruit("orange", 15);
        List<String> expected = List.of("fruit,quantity", "apple:10", "banana:20", "orange:15");
        List<String> result = reportService.generateReport();
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
}
