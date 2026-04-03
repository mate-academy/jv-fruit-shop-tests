package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int BANANA_QUANTITY = 100;
    private static final int APPLE_QUANTITY = 50;
    private static final String HEADER = "fruit,quantity";

    private ReportGenerator reportGenerator;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_validStorage_ok() {
        storage.getFruits().put(BANANA, BANANA_QUANTITY);
        storage.getFruits().put(APPLE, APPLE_QUANTITY);
        String result = reportGenerator.getReport();
        assertTrue(result.contains(HEADER));
        assertTrue(result.contains("banana,100"));
        assertTrue(result.contains("apple,50"));
    }
}
