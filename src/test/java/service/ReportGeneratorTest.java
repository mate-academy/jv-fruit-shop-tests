package service;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private static final String HEADING = "fruit,quantity";
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void generate_validReport_ok() {
        Map<String, Integer> data = Map.of("test1", 10,"test2",20);
        String test1 = "test1,10";
        String test2 = "test2,20";
        String report = reportGenerator.generate(data);
        Assertions.assertTrue(report.contains(HEADING) && report.contains(test1)
                        && report.contains(test2), "Report is not correct");
    }
}
