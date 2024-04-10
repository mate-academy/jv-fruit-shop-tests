package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGenerateImplTest {
    private static ReportGenerate reportGenerate;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeEach
    void beforeAll() {
        reportGenerate = new ReportGenerateImpl();
    }

    @Test
    void report_validFile_Ok() {
        Storage.setFruitBalance(BANANA, 30);
        assertEquals(reportGenerate.generate(), "fruit;quantity\nbanana;30");
        Storage.clear();
        Storage.setFruitBalance(APPLE, 0);
        assertEquals(reportGenerate.generate(), "fruit;quantity\napple;0");
    }
}
