package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import core.basesyntax.report.WriterService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReportCreatorTest {
    private CsvReportCreator csvReportCreator;
    private WriterService writerService;

    @BeforeEach
    public void setUp() {
        writerService = mock(WriterService.class);
        csvReportCreator = new CsvReportCreator(writerService, "output.csv");
    }

    @Test
    public void testGenerateReport() {
        Map<String, Integer> fruitInventory = new HashMap<>();
        fruitInventory.put("Apple", 5);
        fruitInventory.put("Banana", 10);

        String expectedReport = "Fruit,Quantity\nApple,5\nBanana,10\n";

        String actualReport = csvReportCreator.generateReport(fruitInventory);

        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void testCreateReport() throws IOException {
        Map<String, Integer> fruitInventory = new HashMap<>();
        fruitInventory.put("Apple", 5);
        fruitInventory.put("Banana", 10);

        csvReportCreator.createReport(fruitInventory);

        String expectedReport = "Fruit,Quantity\nApple,5\nBanana,10\n";

        verify(writerService).writeToFile(expectedReport, "output.csv");
    }
}
