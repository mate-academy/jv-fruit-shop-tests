package core.basesyntax.service.report;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.FruitStorageImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final FruitStorage storage = new FruitStorageImpl();
    private static ReportService reportService;
    private static final String HEADER = "fruit,quantity";

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl(storage);
        storage.add("apple", 100);
        storage.add("banana", 90);
    }

    @Test
    void createReport_Ok() {
        List<String> reportList = List.of(
                HEADER,
                "banana,90",
                "apple,100");
        List<String> actual = reportService.createReport();
        Assertions.assertEquals(actual, reportList);
    }

    @Test
    void createReport_Null_NotOK() {
        Assertions.assertThrows(RuntimeException.class, () -> reportService.createReport());
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }
}
