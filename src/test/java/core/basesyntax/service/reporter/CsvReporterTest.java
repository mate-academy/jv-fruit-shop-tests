package core.basesyntax.service.reporter;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReporterTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private static Reporter reporter;

    @BeforeEach
    void setUp() {
        reporter = new CsvReporter();
        Storage.FRUITS.put("apple", 20);
    }

    @Test
    void startedWithFirstLine_Ok() {
        String actual = reporter.makeReport();
        Assertions.assertTrue(actual.startsWith(FIRST_LINE));
    }

    @Test
    void containsFruitInformation_Ok() {
        String actual = reporter.makeReport();
        Assertions.assertTrue(actual.contains("apple,20"));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
