package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.service.impl.ReportCreationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreationServiceImplTest {
    private ReportCreationServiceImpl reportCreationService;

    @BeforeEach
    void setUp() {
        reportCreationService = new ReportCreationServiceImpl();
        FruitsStorage.FRUITS_STORAGE.clear();
        FruitsStorage.FRUITS_STORAGE.put("apple", 10);
        FruitsStorage.FRUITS_STORAGE.put("banana", 20);
    }

    @Test
    void getReport_validData_ok() {
        String generatedReport = reportCreationService.getReport();
        assertTrue(generatedReport.contains("apple,10"));
        assertTrue(generatedReport.contains("banana,20"));
    }

    @Test
    void getReport_emptyFruitsStorage_ok() {
        FruitsStorage.FRUITS_STORAGE.clear();
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String generatedReport = reportCreationService.getReport();
        assertEquals(expectedReport, generatedReport);
    }

    @Test
    void getReport_singleFruitInStorage() {
        FruitsStorage.FRUITS_STORAGE.clear();
        FruitsStorage.FRUITS_STORAGE.put("apple", 5);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,5" + System.lineSeparator();
        String generatedReport = reportCreationService.getReport();
        assertEquals(expectedReport, generatedReport);
    }

    @AfterEach
    void tearDown() {
        FruitsStorage.FRUITS_STORAGE.clear();
    }
}
