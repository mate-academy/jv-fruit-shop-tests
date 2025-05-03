package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String VALID_REPORT = """
            fruit,quantity
            banana,222
            apple,40
            watermelon,540""";
    private static final String VALID_REPORT_WITH_EMPTY_STORAGE = "fruit,quantity";
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl(new FruitTransactionDaoImpl());
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void makeReport_validDataReport_ok() {
        Storage.fruitsStorage.put("banana", 222);
        Storage.fruitsStorage.put("apple", 40);
        Storage.fruitsStorage.put("watermelon", 540);
        assertEquals(VALID_REPORT, reportService.makeReport());
    }

    @Test
    void makeReport_withEmptyStorage_ok() {
        assertEquals(VALID_REPORT_WITH_EMPTY_STORAGE, reportService.makeReport());
    }
}
