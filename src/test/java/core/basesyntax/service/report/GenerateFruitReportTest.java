package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class GenerateFruitReportTest {
    private static GenerateFruitReport generateFruitReport;

    @BeforeClass
    public static void beforeClass() throws Exception {
        generateFruitReport = new GenerateFruitReport();
    }

    @Test
    public void generateResultForCommodity_generateLine_True() {
        StringBuilder expectedResult = new StringBuilder("apple,10");
        StringBuilder actualResult = generateFruitReport.generateResultForCommodity("apple", 10);
        assertEquals(actualResult.toString(), expectedResult.toString());
    }
}
