package fruitshop.sevice.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.db.Storage;
import fruitshop.sevice.ReportProcessorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportProcessorImplTest {
    private ReportProcessorService reportProcessorService = new ReportProcessorImpl();

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void generateReport_empty_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, reportProcessorService.generateReport());
    }

    @Test
    void generateReport_valid_ok() {
        Storage.getStorage().put("banana", 40);
        Storage.getStorage().put("apple", 25);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,40"
                + System.lineSeparator()
                + "apple,25"
                + System.lineSeparator();
        assertEquals(expected, reportProcessorService.generateReport());
    }
}
