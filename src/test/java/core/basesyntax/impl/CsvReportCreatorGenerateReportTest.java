package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import core.basesyntax.report.WriterService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReportCreatorGenerateReportTest {
    private CsvReportCreator csvReportCreator;

    @BeforeEach
    public void setUp() {
        WriterService writerService = mock(WriterService.class);
        csvReportCreator = new CsvReportCreator(writerService, "output.csv");
    }

    @Test
    public void testGenerateReportWithFruitInventory() {
        Map<String, Integer> fruitInventory = new HashMap<>();
        fruitInventory.put("Apple", 5);
        fruitInventory.put("Banana", 10);

        String expectedReport = "Fruit,Quantity\nApple,5\nBanana,10\n";

        String actualReport = csvReportCreator.generateReport(fruitInventory);

        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void testGenerateReportWithEmptyFruitInventory() {
        Map<String, Integer> fruitInventory = new HashMap<>();

        String expectedReport = "Fruit,Quantity\n";

        String actualReport = csvReportCreator.generateReport(fruitInventory);

        assertEquals(expectedReport, actualReport);
    }
}
