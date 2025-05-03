package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    void getReport_validStorage_shouldReturnCorrectReport() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();
        Map<String, Integer> storage = Map.of("apple", 10, "banana", 20);

        List<String> report = generator.getReport(storage);

        // Oczekiwana lista, ręcznie posortowana
        List<String> expected = List.of("apple,10", "banana,20")
                .stream()
                .sorted()
                .collect(Collectors.toList());

        // Aktualna lista, posortowana przed porównaniem
        List<String> actual = report.stream()
                .sorted()
                .collect(Collectors.toList());

        assertIterableEquals(expected, actual);
    }
}
