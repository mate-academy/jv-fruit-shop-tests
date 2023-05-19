package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private final ReportService reportService = new ReportServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.listFruits.clear();
    }

    @Test
    public void generateReport_Valid_Ok() {
        Storage.listFruits.put("banan", 8);
        Storage.listFruits.put("apple", 24);

        final String dataOutput = "fruit,quantity";
        String result = dataOutput + System.lineSeparator()
                + "apple,24" + System.lineSeparator()
                + "banan,8" + System.lineSeparator();

        String generatedReport = reportService.generateReport();
        assertEquals(result, generatedReport);
    }
}
