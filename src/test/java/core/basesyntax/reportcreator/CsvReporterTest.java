package core.basesyntax.reportcreator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReporterTest {
    private static final CsvReporter csvReporter = new CsvReporter();
    private static final List<String> VALID_DATA_LIST = new ArrayList<>(2);

    @BeforeClass
    public static void beforeClass() throws Exception {
        VALID_DATA_LIST.add("banana,152");
        VALID_DATA_LIST.add("apple,90");
    }

    @Test
    public void testEmptyDataInput_Ok() {
        String expected = "fruit,quantity\n";
        String actual = csvReporter.createReport(Collections.emptyList());
        assertEquals(expected, actual);
    }

    @Test
    public void testNullDataInput_NotOk() {
        String expected = "fruit,quantity\n";
        String actual = csvReporter.createReport(null);
        assertEquals(expected, actual);
    }

    @Test
    public void testValidDataInput_Ok() {
        String expected = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90\n";
        String actual = csvReporter.createReport(VALID_DATA_LIST);
        assertEquals(expected, actual);
    }
}
