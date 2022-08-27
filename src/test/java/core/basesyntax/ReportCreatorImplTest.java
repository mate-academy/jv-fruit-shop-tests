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

    private static final String EXPECTED_SECOND_REPORT =
            "type,fruit,quantity" + System.lineSeparator()
                    + "apple,100" + System.lineSeparator()
                    + "kiwi,90" + System.lineSeparator()
                    + "fruit,80" + System.lineSeparator()
                    + "peach,70" + System.lineSeparator()
                    + "mango,60" + System.lineSeparator();
    private static final String EXPECTED_EMPTY_REPORT =
            "type,fruit,quantity" + System.lineSeparator();
    private ReportCreator reportCreator;

    @Before
    public void setup() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void createReport_ok() {
        Map<String, Integer> balances = new HashMap<>();
        balances.put("banana", 10);
        balances.put("apple", 15);
        balances.put("lemon", 20);
        balances.put("pineapple", 25);
        balances.put("grape", 30);

        String report = reportCreator.createReport(balances);
        assertEquals(EXPECTED_FIRST_REPORT, report);

        balances.clear();
        balances.put("apple", 100);
        balances.put("kiwi", 90);
        balances.put("fruit", 80);
        balances.put("peach", 70);
        balances.put("mango", 60);
        report = reportCreator.createReport(balances);
        assertEquals(EXPECTED_SECOND_REPORT, report);

    }

    @Test
    public void createEmptyReport_ok() {
        Map<String, Integer> empty = new HashMap<>();
        String report = reportCreator.createReport(empty);
        assertEquals(EXPECTED_EMPTY_REPORT, report);
    }
}
