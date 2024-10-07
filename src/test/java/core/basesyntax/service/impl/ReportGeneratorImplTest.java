package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private ReportGeneratorImpl reportGenerator;
    private StorageServiceImpl storageService;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        storageService = new StorageServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void generateReport_validReportFormat_ok() {
        storageService.addFruit(new Fruit("apple"), 10);
        storageService.addFruit(new Fruit("banana"), 5);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,5";
        String actualReport = reportGenerator.generateReport();
        Set<String> expectedLines = new HashSet<>(Arrays.asList(expectedReport
                .split(System.lineSeparator())));
        Set<String> actualLines = new HashSet<>(Arrays.asList(actualReport
                .split(System.lineSeparator())));
        assertEquals(expectedLines, actualLines);
    }

    @Test
    void generateReport_oneFruitInReport_ok() {
        storageService.addFruit(new Fruit("orange"), 7);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "orange,7";
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport.trim(), actualReport.trim());
    }
}
