package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void report_validReport_Ok() {
        Storage.setFruitBalance(BANANA, 90);
        assertEquals(reportGenerator.report(), "fruit,quantity\nbanana,90");
        Storage.clear();
        Storage.setFruitBalance(APPLE,12);
        assertEquals(reportGenerator.report(), "fruit,quantity\napple,12");
    }
}
