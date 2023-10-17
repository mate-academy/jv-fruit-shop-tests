package core.basesyntax.service.report.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.LocalStorageFruitDao;
import core.basesyntax.db.LocalStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReportServiceTest {
    private static final String EMPTY_REPORT = "fruit,quantity" + System.lineSeparator();

    private static CsvReportService csvReportService;

    @BeforeAll
    static void beforeAll() {
        FruitDao fruitDao = new LocalStorageFruitDao();
        csvReportService = new CsvReportService(fruitDao);
        LocalStorage.FRUITS.clear();
    }

    @AfterEach
    void tearDown() {
        LocalStorage.FRUITS.clear();
    }

    @Test
    void generateReport_emptyStorage_ok() {
        String actual = csvReportService.generateReport();
        assertEquals(EMPTY_REPORT, actual);
    }

    @Test
    void generateReport_notEmptyStorage_ok() {
        LocalStorage.FRUITS.put("apple", 10);
        LocalStorage.FRUITS.put("banana", 20);
        LocalStorage.FRUITS.put("orange", 30);
        String actual = csvReportService.generateReport();
        assertTrue(() -> actual.contains("apple,10")
                        && actual.contains("banana,20")
                        && actual.contains("orange,30"));
    }
}
