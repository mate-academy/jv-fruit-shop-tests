package core.basesyntax.reportcreator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReporterTest {
    private static final CsvReporter csvReporter = new CsvReporter();
    private static final List<String> validList = new ArrayList<>(2);
    private static final String REPORT_TITLE = "fruit,quantity\n";

    @BeforeClass
    public static void beforeClass() throws Exception {
        validList.add("banana,152");
        validList.add("apple,90");
    }

    @Test
    public void testEmptyDataInput_Ok() {
        String expected = REPORT_TITLE;
        String actual = csvReporter.createReport(Collections.emptyList());
        assertEquals(expected, actual);
    }

    @Test
    public void testNullDataInput_NotOk() {
        String expected = REPORT_TITLE;
        String actual = csvReporter.createReport(null);
        assertEquals(expected, actual);
    }

    @Test
    public void testValidDataInput_Ok() {
        String expected = REPORT_TITLE
                + "banana,152\n"
                + "apple,90\n";
        String actual = csvReporter.createReport(validList);
        assertEquals(expected, actual);
    }
}
