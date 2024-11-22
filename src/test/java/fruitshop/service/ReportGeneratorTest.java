package fruitshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.db.Storage;
import fruitshop.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void generate_validData_ok() {
        Storage.fruits.put("apple", 50);
        Storage.fruits.put("banana", 30);

        String report = reportGenerator.generate();

        String expected = "fruit,quantity\napple,50\nbanana,30\n";
        assertEquals(expected, report);
    }

    @Test
    void generate_emptyStorage_ok() {
        String report = reportGenerator.generate();
        String expected = "fruit,quantity\n";
        assertEquals(expected, report);
    }
}
