package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import data.base.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGeneratorService;
import service.impl.ReportGeneratorServiceImpl;

public class ReportGeneratorServiceImplTest {

    private ReportGeneratorService reportGeneratorService;

    @BeforeEach
    void setUp() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Test
    void createReport_shouldGenerateCorrectReport() {
        Storage.STORAGE.put("banana", 90);
        Storage.STORAGE.put("apple", 100);
        String report = reportGeneratorService.createReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,90" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();

        assertEquals(expectedReport, report);
    }

    @Test
    void createReport_shouldGenerateEmptyReport() {
        String report = reportGeneratorService.createReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        assertEquals(expectedReport, report);
    }
}
