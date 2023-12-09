package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static Storage storage;

    @BeforeAll
    public static void setReportService() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    public void setStorage() {
        storage = new StorageImpl();
    }

    @Test
    public void createReport_validData() {
        storage.put("banana", 192);
        storage.put("apple", 34);
        storage.put("coconut", 12);

        assertDoesNotThrow(() -> reportService.createReport(storage));
    }
}
