package core.basesyntax.services.reportgenerator;

import core.basesyntax.db.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private static IReportGenerator reportGenerator;

    @BeforeAll
    public static void beforeAll() {
        reportGenerator = new ReportGenerator();
    }

    @AfterEach
    public void tearDown() {
        DB.getFruitsDB().clear();
    }

    @Test
    public void getReport_shouldReturnOnlyHeaders_ifDatabaseIsEmpty() {
        String expected = "fruit,quantity";

        String result = reportGenerator.getReport();

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getReport_shouldGenerateReport_fromDatabaseData() {
        DB.getFruitsDB().put("apple", 5);

        String expected = "fruit,quantity" + System.lineSeparator() + "apple,5";

        String result = reportGenerator.getReport();

        Assertions.assertEquals(expected, result);
    }
}
