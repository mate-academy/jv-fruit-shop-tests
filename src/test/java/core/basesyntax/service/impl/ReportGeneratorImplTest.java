package core.basesyntax.service.impl;

import core.basesyntax.model.ResultData;
import core.basesyntax.service.ReportGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private static List<ResultData> input = new ArrayList<>();

    @AfterEach
    void tearDown() {
        input.clear();
    }

    @Test
    void generateReport_Ok() {
        input.add(new ResultData("banana", 107));
        input.add(new ResultData("apple",110));

        String expectedResult = "fruit,quantity"
                + System.lineSeparator()
                + "banana,107\napple,110\n";
        String actualResult = reportGenerator.getReport(input);
        System.out.println(expectedResult);
        System.out.println(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
