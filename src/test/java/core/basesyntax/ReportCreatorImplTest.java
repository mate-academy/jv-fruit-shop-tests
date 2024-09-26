package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.ReportCreatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String EXPECTED_FIRST_REPORT =
            "type,fruit,quantity" + System.lineSeparator()
                    + "banana,10" + System.lineSeparator()
                    + "apple,15" + System.lineSeparator()
                    + "lemon,20" + System.lineSeparator()
                    + "pineapple,25" + System.lineSeparator()
                    + "grape,30" + System.lineSeparator();
    private static final String EXPECTED_EMPTY_REPORT =
            "type,fruit,quantity" + System.lineSeparator();
    private ReportCreator reportCreator;

    @Before
    public void setup() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void createReport_TestInputs_Ok() {
        Map<String, Integer> balances = new HashMap<>();
        balances.put("banana", 10);
        balances.put("apple", 15);
        balances.put("lemon", 20);
        balances.put("pineapple", 25);
        balances.put("grape", 30);
        String report = reportCreator.createReport(balances);
        assertEquals(EXPECTED_FIRST_REPORT, report);
    }

    @Test
    public void createReport_WithEmptyInputs_Ok() {
        Map<String, Integer> empty = new HashMap<>();
        String report = reportCreator.createReport(empty);
        assertEquals(EXPECTED_EMPTY_REPORT, report);
    }
}
