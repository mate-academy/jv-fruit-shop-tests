package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final Storage storage = new StorageImpl();
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void generateReport_Ok() {
        storage.setValue("orange", 50);

        String expected = "fruit,quantity\norange=50";
        String actual = reportService.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_NullFruit_NotOk() {
        storage.setValue(null, 90);

        assertThrows(RuntimeException.class, () ->
                reportService.generateReport());
    }

    @AfterEach
    void afterEach() {
        new StorageImpl().clear();
    }
}
