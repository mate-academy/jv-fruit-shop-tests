package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGenerationServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGenerationServiceImplTest {
    private static final String HEADER_LINE = "fruit,quantity";
    private ReportGenerationServiceImpl reportGenerationService;

    @BeforeEach
    public void setup() {
        reportGenerationService = new ReportGenerationServiceImpl();
        Storage.FRUITS.clear();
    }

    @Test
    public void generateReport_validStorageData_ok() {
        Map<String, Integer> fruits = Map.of(
                "Apple", 5,
                "Banana", 10
        );
        Storage.FRUITS.putAll(fruits);
        String expectedReport = HEADER_LINE + System.lineSeparator()
                + "Apple,5" + System.lineSeparator()
                + "Banana,10" + System.lineSeparator();
        String actualReport = reportGenerationService.generateReport();
        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void generateReport_emptyStorageData_ok() {
        String expectedReport = HEADER_LINE + System.lineSeparator();
        String actualReport = reportGenerationService.generateReport();
        assertEquals(expectedReport, actualReport);
    }
}
