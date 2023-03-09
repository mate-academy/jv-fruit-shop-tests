package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import core.basesyntax.database.Storage;
import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

public class ReportServiceImplTest {
    private static Map<String, Integer> fruitStorage;
    private static ReportService reportService;
    private static MockedStatic<Storage> mockStorage;

    @BeforeClass
    public static void beforeClass() {
        mockStorage = mockStatic(Storage.class);
        fruitStorage = new HashMap<>();
    }

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void clearStorage() {
        fruitStorage.clear();
    }

    @AfterClass
    public static void afterClass() {
        mockStorage.close();
    }

    @Test
    public void getReport_getReportSomeData_Ok() {
        //given
        fruitStorage.put("banana", 50);
        fruitStorage.put("apple", 200);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "apple,200" + System.lineSeparator();
        when(Storage.getFruitStorage()).thenReturn(fruitStorage);

        //when
        String actual = reportService.getReport();

        //then
        assertEquals("Incorrect data after writing report: ",
                expected, actual);
    }

    @Test
    public void getReport_getReportEmptyStorage_Ok() {
        //given
        String expected = "fruit,quantity" + System.lineSeparator();
        when(Storage.getFruitStorage()).thenReturn(fruitStorage);

        //when
        String actual = reportService.getReport();

        //then
        assertEquals("Incorrect data after writing report: ",
                expected, actual);
    }
}

