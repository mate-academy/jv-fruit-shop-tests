package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
    }

    @Test
    public void createReport_Okay() {
        Map<String, Integer> calculationMap = Storage.getCalculationMap();
        calculationMap.put("apple", 10);
        calculationMap.put("banana", 100);

        String expected = new StringBuilder().append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,100")
                .append(System.lineSeparator())
                .append("apple,10")
                .toString();
        String report = reportService.createReport();
        assertEquals(expected, report);
    }
}
