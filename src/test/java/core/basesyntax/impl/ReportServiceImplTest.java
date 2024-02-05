package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitstorge.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

    @Test
    void createReport() {

        ReportService reportService = new ReportServiceImpl();

        FruitStorage.fruitStorage.put("apple", 10);
        FruitStorage.fruitStorage.put("banana", 5);

        String report = reportService.createReport();

        String expectedReport = "fruit, quantity" + System.lineSeparator()
                + "banana 5" + System.lineSeparator()
                + "apple 10";

        assertEquals(expectedReport, report);
    }
}
