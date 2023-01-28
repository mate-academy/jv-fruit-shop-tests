package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.service.ReportSevice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportSeviceImplTest {
    private static final int INDEX_FIRST_LINE = 0;
    private static final int INDEX_SECOND_LINE = 1;
    private static final Map<String, Integer> VAILID_DATA = Map.of("apple", 10);
    private static final List<String> EXPECTED_DATA = List.of("fruit,quantity", "apple,10");
    private static final List<String> EMPTY_DATA_FILE = List.of("fruit,quantity");
    private static ReportSevice reportSevice;

    @BeforeClass
    public static void init() {
        reportSevice = new ReportSeviceImpl();
    }

    @Test
    public void reportGenerator_Ok() {
        List<String> actual = reportSevice.reportGenerator(VAILID_DATA);
        assertEquals(actual.get(INDEX_FIRST_LINE), EXPECTED_DATA.get(INDEX_FIRST_LINE));
        assertEquals(actual.get(INDEX_SECOND_LINE), EXPECTED_DATA.get(INDEX_SECOND_LINE));
    }

    @Test
    public void reportGenerator_invalidData_notOk() {
        List<String> actual = reportSevice.reportGenerator(new HashMap<>());
        assertEquals(actual, EMPTY_DATA_FILE);
    }

    @Test(expected = NullPointerException.class)
    public void reportGenerator_nullData_notOk() {
        reportSevice.reportGenerator(null);
    }
}
