package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGenerateImplTest {
    private static ReportGenerate reportGenerate;
    private static final String BANANA = "banana";

    @BeforeAll
    static void beforeAll() {
        reportGenerate = new ReportGenerateImpl();
    }

    @Test
    void report_Ok() {
        Storage.setFruitBalance(BANANA, 30);
        assertEquals(reportGenerate.report(), "fruit;quantity\nbanana;30");
        Storage.clear();
        Storage.setFruitBalance("apple", 0);
        assertEquals(reportGenerate.report(), "fruit;quantity\napple;0");
    }
}
