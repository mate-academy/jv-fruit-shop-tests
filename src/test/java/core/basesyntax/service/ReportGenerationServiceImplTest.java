package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGenerationServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGenerationServiceImplTest {
    private ReportGenerationServiceImpl reportGenerationService;

    @BeforeEach
    public void setup() {
        reportGenerationService = new ReportGenerationServiceImpl();
        Storage.FRUITS.clear();
    }

    @Test
    public void generateReport_validStorageData_ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("Apple", 5);
        fruits.put("Banana", 10);
        Storage.FRUITS.putAll(fruits);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "Apple,5" + System.lineSeparator()
                + "Banana,10" + System.lineSeparator();
        String actualReport = reportGenerationService.generateReport();
        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void generateReport_emptyStorageData_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerationService.generateReport();
        assertEquals(expectedReport, actualReport);
    }
}
