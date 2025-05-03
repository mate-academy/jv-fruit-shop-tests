package core.basesyntax.service.impl;

import core.basesyntax.model.Report;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportMapperTest {
    private static ReportMapper mapper;

    @BeforeAll
    static void beforeAll() {
        mapper = new ReportMapper();
    }

    @Test
    void toObject_validInput_ok() {
        Map<String, Integer> fruitsMap = Map.of("banana", 3, "apple", 5);
        Report report = new Report(fruitsMap);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,3"
                + System.lineSeparator() + "apple,5";

        String actual = mapper.toObject(report);

        assertCsvContentEquals(expected, actual,
                "The formatted string should match the CSV format");
    }

    @Test
    void toObject_emptyReport_shouldReturnHeader() {
        Map<String, Integer> fruitsMap = Map.of();
        Report report = new Report(fruitsMap);
        String expected = "fruit,quantity";

        String actual = mapper.toObject(report);

        assertCsvContentEquals(expected, actual, "The formatted string should contain "
                + "only header, if report is empty");
    }

    @Test
    void toObject_singleEntryReport_shouldReturnSingleEntryString() {
        Map<String, Integer> fruitsMap = Map.of("orange", 21);
        Report report = new Report(fruitsMap);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "orange,21";

        String actual = mapper.toObject(report);

        assertCsvContentEquals(expected, actual, "The formatted string should contain "
                + "a header and a single entry line");
    }

    /**
     * This method sorts string lines and compares them. This is required, as there is no
     * exact order in which the lines are written
     *
     * @param expected expected result of method call
     * @param actual   actual result of method call
     * @param message  message for assertion
     */
    private void assertCsvContentEquals(String expected, String actual, String message) {
        List<String> expectedLines = sortLines(expected);
        List<String> actualLines = sortLines(actual);
        Assertions.assertEquals(expectedLines, actualLines, message);
    }

    private static List<String> sortLines(String actual) {
        return actual.lines()
                .sorted()
                .collect(Collectors.toList());
    }
}
