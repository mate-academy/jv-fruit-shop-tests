package core.basesyntax;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl(); // Ініціалізація перед кожним тестом
    }

    @Test
    void getReport_validValue_ok() {
        Map<String, Integer> validData = Map.of(
                "apple", 64,
                "banana", 45
        );
        String actual = reportGenerator.getReport(validData);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,64" + System.lineSeparator()
                + "banana,45" + System.lineSeparator();

        String[] actualLines = actual.split(System.lineSeparator());
        String[] expectedLines = expected.split(System.lineSeparator());

        Arrays.sort(actualLines);
        Arrays.sort(expectedLines);

        Assertions.assertArrayEquals(expectedLines, actualLines);
    }

    @Test
    void getReport_emptyValue_ok() {
        Map<String, Integer> emptyData = Map.of();
        String actual = reportGenerator.getReport(emptyData);
        String expected = "fruit,quantity" + System.lineSeparator();
        Assertions.assertEquals(expected, actual);
    }
}
