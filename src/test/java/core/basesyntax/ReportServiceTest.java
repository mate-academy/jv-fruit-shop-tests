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
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String COCONUT = "coconut";
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
        storage.put(BANANA, 192);
        storage.put(APPLE, 34);
        storage.put(COCONUT, 12);

        assertDoesNotThrow(() -> reportService.createReport(storage));
    }
}
