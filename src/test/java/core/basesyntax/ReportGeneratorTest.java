package core.basesyntax;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @Test
    void valid_value_ok() {
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
    void empty_value_ok() {
        Map<String, Integer> emptyData = Map.of();
        String actual = reportGenerator.getReport(emptyData);
        String expected = "fruit,quantity" + System.lineSeparator();
        Assertions.assertEquals(expected, actual);
    }
}
