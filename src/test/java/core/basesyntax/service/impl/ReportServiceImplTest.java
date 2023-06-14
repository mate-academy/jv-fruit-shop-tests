package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.DataStorage;
import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruits,quantity" + System.lineSeparator();
    private static final String FIRST_TYPE_FRUIT = "apple";
    private static final String SECOND_TYPE_FRUIT = "banana";
    private static final int FIRST_TYPE_QUANTITY = 90;
    private static final int SECOND_TYPE_QUANTITY = 152;
    private static final int INVALID_QUANTITY = -50;
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_Work_Ok() {
        DataStorage.fruitStorageMap.put(FIRST_TYPE_FRUIT, FIRST_TYPE_QUANTITY);
        DataStorage.fruitStorageMap.put(SECOND_TYPE_FRUIT, SECOND_TYPE_QUANTITY);
        String actual = reportService.createReport();
        String expected = HEADER + "banana,152"
                + System.lineSeparator() + "apple,90"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_InvalidData_NotOk() {
        DataStorage.fruitStorageMap.put(FIRST_TYPE_FRUIT, INVALID_QUANTITY);
        DataStorage.fruitStorageMap.put(SECOND_TYPE_FRUIT, SECOND_TYPE_QUANTITY);
        String actual = reportService.createReport();
        String expected = HEADER + SECOND_TYPE_FRUIT + ","
                + SECOND_TYPE_QUANTITY + System.lineSeparator()
                + "apple,-50" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
