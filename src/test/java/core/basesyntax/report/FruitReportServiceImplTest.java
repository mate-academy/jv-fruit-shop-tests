package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitReportServiceImplTest {

    @Test
    void getReport_shouldReturnFormattedReport() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put("Apple", 50);
        fruitStorage.put("Banana", 100);

        FruitReportServiceImpl reportService = new FruitReportServiceImpl(fruitStorage);

        String expectedReport = "fruit,quantity\n"
                + "Apple,50\n"
                + "Banana,100\n";
        assertEquals(expectedReport, reportService.getReport());
    }

    @Test
    void getReport_shouldHandleEmptyStorage() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        FruitReportServiceImpl reportService = new FruitReportServiceImpl(fruitStorage);

        String expectedReport = "fruit,quantity\n";
        assertEquals(expectedReport, reportService.getReport());
    }

    @Test
    void getReport_shouldHandleSingleEntry() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put("Orange", 30);

        FruitReportServiceImpl reportService = new FruitReportServiceImpl(fruitStorage);

        String expectedReport = "fruit,quantity\n"
                + "Orange,30\n";
        assertEquals(expectedReport, reportService.getReport());
    }
}
