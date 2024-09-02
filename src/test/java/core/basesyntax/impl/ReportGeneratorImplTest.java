package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 40;
    private static final int BANANA_QUANTITY = 20;
    private static final String HEADER = "fruit,quantity";
    private static final String DELIMITER = ",";
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.getAllFruits();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_correct_ok() {
        Storage.addFruit(BANANA, BANANA_QUANTITY);
        Storage.addFruit(APPLE, APPLE_QUANTITY);
        String report = reportGenerator.getReport();
        System.out.println("Actual Report:\n" + report);
        String expectedReport = HEADER + System.lineSeparator()
                + BANANA + DELIMITER + 20 + System.lineSeparator()
                + APPLE + DELIMITER + 40 + System.lineSeparator();
        System.out.println("Expected Report:\n" + expectedReport);
        assertEquals(expectedReport, report);
    }
}
