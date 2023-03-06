package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreated;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatedImplTest {
    private static final String RESUL = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static ReportCreated reportCreated;
    private static final Fruit[] fruits = new Fruit[] {new Fruit("banana",152),
            new Fruit("apple",90)};

    @BeforeClass
    public static void beforeClass() {
        reportCreated = new ReportCreatedImpl();
    }

    @Test
    public void report_correctData_isOk() {
        assertEquals(reportCreated.createReport(fruits), RESUL);
    }

    @Test (expected = RuntimeException.class)
    public void report_nullData_isOk() {
        reportCreated.createReport(null);
    }
}
