package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.StorageService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final List<String> EXPECTED_VALID_REPORT = List.of(
            "fruit,quantity",
            "banana,107",
            "apple,100"
    );

    private static final List<String> EXPECTED_EMPTY_REPORT = List.of(
            "fruit,quantity"
    );
    private StorageService storageService;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        reportService = new ReportServiceImpl(storageService);
    }

    @Test
    void generateReport_ValidData_Ok() {
        storageService.add("banana", 107);
        storageService.add("apple", 100);

        List<String> actualReport = reportService.generateReport();
        assertEquals(EXPECTED_VALID_REPORT, actualReport);
    }

    @Test
    void generateReport_NoDataInStorage_Ok() {
        List<String> actualReport = reportService.generateReport();
        assertEquals(EXPECTED_EMPTY_REPORT, actualReport);
    }

    @Test
    void generateReport_NullStorageInstance_Ok() {
        reportService = new ReportServiceImpl(null);

        List<String> actualReport = reportService.generateReport();
        assertEquals(EXPECTED_EMPTY_REPORT, actualReport);
    }
}
