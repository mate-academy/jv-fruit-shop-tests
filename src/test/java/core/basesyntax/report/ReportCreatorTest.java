package core.basesyntax.report;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreator();
        fruitStorage.clear();
    }

    @Test
    void reportCreator_createReportWithValidData_ok() {
        fruitStorage.put("melon", 100);
        fruitStorage.put("apple", 100);
        fruitStorage.put("banana", 100);
        Set<String> expectedLines = Set.of(
                "fruit,quantity",
                "apple,100",
                "melon,100",
                "banana,100"
        );
        String report = reportCreator.createReport();
        Set<String> actualLines = new HashSet<>(List.of(report.split(System.lineSeparator())));
        assertEquals(expectedLines, actualLines);
    }

    @Test
    void reportCreator_createReportWithInvalidData_notOk() {
        fruitStorage.put(null, 100);
        fruitStorage.put("banana", null);
        assertThrows(IllegalArgumentException.class,
                () -> reportCreator.createReport());
    }
}
