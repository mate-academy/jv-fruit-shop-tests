package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.serviceimpl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_validData_correctReport() {
        Storage.add("pineapple", 40);
        Storage.add("apple", 30);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,30" + System.lineSeparator()
                + "pineapple,40" + System.lineSeparator();

        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }
}
