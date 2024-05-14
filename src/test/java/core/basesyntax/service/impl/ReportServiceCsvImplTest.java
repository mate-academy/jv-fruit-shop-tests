package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceCsvImplTest {

    private ReportService reportService;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setup() {
        fruitStorageDao = new FruitStorageDaoImpl();
        reportService = new ReportServiceCsvImpl(fruitStorageDao);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void createReport_ValidStorage_Ok() {
        FruitStorage.fruitStorage.put("apple", 30);
        FruitStorage.fruitStorage.put("banana", 120);
        String output = reportService.createReport();
        String expected = "fruit,quantity\nbanana,120\napple,30\n";
        assertEquals(expected, output, "Report not as required");
    }

    @Test
    void createReport_EmptyStorage_NotOk() {
        FruitStorage.fruitStorage.clear();
        assertThrows(RuntimeException.class,
                () -> reportService.createReport(),
                "Custom RuntimeException expected");
    }
}
