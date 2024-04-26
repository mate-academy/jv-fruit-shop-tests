package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import org.junit.jupiter.api.AfterAll;
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
    public void generateReport_Test() {
        storage.setValue("orange", 50);

        String expected = "fruit,quantity\norange=50";
        String actual = reportService.generateReport();

        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        new StorageImpl().clear();
    }
}
