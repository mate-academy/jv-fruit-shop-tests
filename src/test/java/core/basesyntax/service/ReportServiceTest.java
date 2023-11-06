package core.basesyntax.service;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImp;
import core.basesyntax.service.imp.Reporter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT_HEAD = "fruit,quantity";

    private static ReportService reportService;
    private static StorageDao storageDao;
    private static Map<String, Integer> storage;

    @BeforeAll
    static void beforeAll() {
        storage = new HashMap<>();
        storageDao = new StorageDaoImp(storage);
        reportService = new Reporter(storageDao);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void getGoodsStockCsv_validData_ok() {
        storage.put("banana", 50);
        storage.put("apple", 100);
        List<String> expectedsList = List.of(
                REPORT_HEAD + LINE_SEPARATOR,
                "banana,50" + LINE_SEPARATOR,
                "apple,100" + LINE_SEPARATOR);
        List<String> actualList = reportService.getGoodsStockCsv();
        Assertions. assertIterableEquals(expectedsList, actualList);
    }
}
