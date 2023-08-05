package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitsStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreationServiceImplTest {
    private ReportCreationServiceImpl reportCreationService;

    @BeforeEach
    public void setUp() {
        reportCreationService = new ReportCreationServiceImpl();
        FruitsStorage.fruitsStorage.clear();
        FruitsStorage.fruitsStorage.put("apple", 10);
        FruitsStorage.fruitsStorage.put("banana", 20);
    }

    @Test
    public void getReport_ValidData_ok() {
        String generatedReport = reportCreationService.getReport();
        assertTrue(generatedReport.contains("apple,10"));
        assertTrue(generatedReport.contains("banana,20"));
    }

    @Test
    public void getReport_EmptyFruitsStorage_ok() {
        FruitsStorage.fruitsStorage.clear();
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String generatedReport = reportCreationService.getReport();
        assertEquals(expectedReport, generatedReport);
    }

    @Test
    public void getReport_SingleFruitInStorage() {
        FruitsStorage.fruitsStorage.clear();
        FruitsStorage.fruitsStorage.put("apple", 5);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                        + "apple,5" + System.lineSeparator();
        String generatedReport = reportCreationService.getReport();
        assertEquals(expectedReport, generatedReport);
    }

    @AfterEach
    public void tearDown() {
        FruitsStorage.fruitsStorage.clear();
    }
}
