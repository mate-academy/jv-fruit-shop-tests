package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReporterServiceImplTest {
    private static final String REPORT_HEADER = "fruit,quantity" + System.lineSeparator();
    private static final String INVALID_REPORT_HEADER = "vegetable,quantity" + System.lineSeparator();
    private static final String VALID_EXPECTED_REPORT =
            REPORT_HEADER
                    + "banana,152" + System.lineSeparator()
                    + "apple,90" + System.lineSeparator();

    private ReporterServiceImpl reporterService;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl(new Storage());
        storageService.add("banana", 152);
        storageService.add("apple", 90);
        reporterService = new ReporterServiceImpl(storageService);
    }

    @Test
    void createReport_CorrectFormat_Ok() {
        String actualReport = reporterService.createReport();
        assertEquals(VALID_EXPECTED_REPORT, actualReport);
    }

    @Test
    void createReport_InvalidHeader_NotOk() {
        String actualReport = reporterService.createReport();
        assertEquals(VALID_EXPECTED_REPORT, actualReport);
        assertNotEquals(INVALID_REPORT_HEADER, actualReport);
    }
}
