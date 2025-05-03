package core.basesyntax.service.impl;

import core.basesyntax.db.storage.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private static final String EXPECTED_RESULT = """
                fruit,quantity
                apple,100
                banana,20
                orange,30
                """;
    private static final String EMPTY_RESULT = """
                fruit,quantity
                """;

    @BeforeEach
    void setUp() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("orange", 30);
        Storage.fruits.put("apple", 100);
    }

    @AfterEach
    void reset() {
        Storage.fruits.clear();
    }

    @Test
    void generatingReport_OK() {
        ReportGenerator report = new ReportGeneratorImpl();
        String actualResult = report.getReport();
        Assertions.assertEquals(EXPECTED_RESULT, actualResult);
    }

    @Test
    void generatingEmptyReport_OK() {
        ReportGenerator report = new ReportGeneratorImpl();
        Storage.fruits.clear();
        String actualResult = report.getReport();
        Assertions.assertEquals(EMPTY_RESULT, actualResult);
    }
}
