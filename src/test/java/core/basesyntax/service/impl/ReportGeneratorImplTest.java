package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String COLUMNS_NAME = "fruit,quantity";
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        Storage.fruits.clear();
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("orange", 200);
    }

    @Test
    void getReport_ValidStorageData_Ok() {
        String actual = reportGenerator.getReport();

        Set<String> expected = Set.of(
                COLUMNS_NAME,
                "apple,100",
                "orange,200"
        );

        Set<String> actualLines = Set.of(actual.split(System.lineSeparator()));

        assertEquals(expected,
                actualLines,
                "Report does not match expected values");
    }

}
