package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitDao;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DailyReportImpTest {
    private FruitDao fruitDao;
    private DailyReport dailyReport;

    @BeforeEach
    void setUp() {
        fruitDao = mock(FruitDao.class);
        dailyReport = new DailyReportImp(fruitDao);
    }

    @Test
    void testReport() {
        List<String> csvData = Arrays.asList(
                "p,apple,1",
                "s,orange,3",
                "s,banana,2",
                "s,apple,5",
                "p,banana,1",
                "p,orange,1",
                "r,apple,1",
                "r,banana,1",
                "r,orange,1"
        );
        when(fruitDao.getCsv()).thenReturn(csvData);
        List<String> result = dailyReport.report();
        assertEquals(3, result.size());
        assertEquals("banana,2", result.get(0));
        assertEquals("orange,3", result.get(1));
        assertEquals("apple,5", result.get(2));
    }
}
