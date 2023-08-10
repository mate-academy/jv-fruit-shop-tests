package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String TITLE = "fruit,quantity"
            + System.lineSeparator();
    private static final String BALANCE = "b,banana,20";
    private static final String RETURN = "r,apple,10";
    private static final String SUPPLY = "s,banana,100";
    private static final String PURCHASE = "p,apple,20";
    private static List<String> dataFromFile;
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,100"
            + System.lineSeparator()
            + "apple,200"
            + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
        Storage.storage.put("banana",100);
        Storage.storage.put("apple",200);
    }

    @BeforeEach
    void setUp() {
        dataFromFile = List.of(TITLE,BALANCE,RETURN,SUPPLY,PURCHASE);
        Storage.storage.put("banana",100);
        Storage.storage.put("apple",200);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void generateReport_storageIsEmpty_Ok() {
        Storage.storage.clear();
        assertEquals(TITLE,reportService.generateReport());
    }

    @Test
    void generateReport_storageIsNotEmpty_Ok() {
        assertEquals(REPORT, reportService.generateReport());
    }
}
