package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.ResultData;
import core.basesyntax.service.ReportGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private List<ResultData> input = new ArrayList<>();

    @AfterEach
    void tearDown() {
        input.clear();
    }

    @Test
    void generateReport_Ok() {
        input.add(new ResultData("banana", 107));
        input.add(new ResultData("apple", 110));

        String expectedResult = "fruit,quantity"
                + System.lineSeparator()
                + "banana,107"
                + System.lineSeparator()
                + "apple,110"
                + System.lineSeparator();
        String actualResult = reportGenerator.getReport(input);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateReportFromEmptyList() {
        String expectedResult = "fruit,quantity"
                + System.lineSeparator();
        String actualResult = reportGenerator.getReport(input);
        assertEquals(expectedResult, actualResult);
    }
}
