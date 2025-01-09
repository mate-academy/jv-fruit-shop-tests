package core.basesyntax.utils.impl;

import core.basesyntax.model.ResultData;
import core.basesyntax.utils.ReportGenerator;
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

        String expectedResult = "fruit,quantity\nbanana,107\napple,110\n";
        String actualResult = reportGenerator.getReport(input);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}
