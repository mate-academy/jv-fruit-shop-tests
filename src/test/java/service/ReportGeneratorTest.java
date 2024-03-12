package service;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private static final Map<String, Integer> DATA = Map.of("test", 10,"test2",20);
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void generate_validReport_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "test2,20" + System.lineSeparator()
                + "test,10" + System.lineSeparator();
        Assertions.assertEquals(expected, reportGenerator.generate(DATA));
    }
}
