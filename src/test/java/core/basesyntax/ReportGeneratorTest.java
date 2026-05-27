package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {

    private static final ReportGenerator REPORT_GENERATOR = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity\n";

        String actual = REPORT_GENERATOR.getReport();

        assertEquals(expected, actual);
    }

    @Test
    void getReport_singleFruit_ok() {
        Storage.getFruits().put("apple", 10);

        String expected = "fruit,quantity\napple,10\n";

        String actual = REPORT_GENERATOR.getReport();

        assertEquals(expected, actual);
    }

    @Test
    void getReport_multipleFruits_ok() {
        Storage.getFruits().put("apple", 10);
        Storage.getFruits().put("banana", 20);

        String result = REPORT_GENERATOR.getReport();

        assertTrue(result.contains("fruit,quantity\n"));
        assertTrue(result.contains("apple,10\n"));
        assertTrue(result.contains("banana,20\n"));
    }
}
