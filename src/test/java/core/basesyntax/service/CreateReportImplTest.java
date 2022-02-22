package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.DataStorage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportImplTest {
    private static CreateReportImpl createReport;
    private static DataStorage dataStorage = new DataStorage();

    private final String currentReportText = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator() + "apple,150";

    @BeforeClass
    public static void beforeClass() {
        createReport = new CreateReportImpl();
    }

    @Before
    public void cleanMap() {
        DataStorage.fruitMap.clear();
    }

    @Test(expected = RuntimeException.class)
    public void report_text_notOk() {
        DataStorage.fruitMap.put("", 150);
        DataStorage.fruitMap.put("banana", null);
        final String excepted = currentReportText;
        final String actual = createReport.report();
        assertEquals(excepted, actual);
    }

    @Test
    public void report_text_ok() {
        DataStorage.fruitMap.put("apple", 150);
        DataStorage.fruitMap.put("banana", 100);
        final String excepted = currentReportText;
        final String actual = createReport.report();
        assertEquals(excepted, actual);
    }
}
