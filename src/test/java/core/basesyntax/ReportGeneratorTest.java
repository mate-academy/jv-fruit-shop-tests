package core.basesyntax;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static final String EXPECTED_REPORT = "fruit,quantity\n"
            + "orange,100\n"
            + "apple,50";
    private static final String ORANGE = "orange";
    private static final String APPLE = "apple";
    private static final int ORANGE_QUANTITY = 100;
    private static final int APPLE_QUANTITY = 50;
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void initialize_reportGenerator() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void reportGenerator_generateReport_nullNotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> reportGenerator.generateReport(null));
    }

    @Test
    void reportGenerator_generateReport_methodExecution() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put(ORANGE, ORANGE_QUANTITY);
        fruits.put(APPLE, APPLE_QUANTITY);
        String actualReport = reportGenerator.generateReport(fruits);
        Assertions.assertEquals(EXPECTED_REPORT, actualReport);
    }
}
