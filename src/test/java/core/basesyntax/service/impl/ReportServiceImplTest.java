package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitDto;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String RESUL = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static ReportService reportCreated;
    private static List<FruitDto> fruits;

    @BeforeClass
    public static void beforeClass() {
        reportCreated = new ReportServiceImpl();
        fruits = new ArrayList<>();
        fruits.add(new FruitDto("banana",152));
        fruits.add(new FruitDto("apple",90));
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
