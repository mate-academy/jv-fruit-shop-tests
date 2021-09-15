package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class ReportTest {

    private static List<String> testInputList;
    private static ReportService reportService;
    private static final String[] testInput = new String[] {"b,banana,20",
            "s,banana,100", "p,banana,100", "r,banana,100"};
    private static final String[] resultReport = new String[] {"fruit,quantity",
            "banana,120"};
    private static final String[] emptyResultReport = new String[] {"fruit,quantity"};

    @Before
    public void initialize() {
        testInputList = Stream.of(testInput).collect(Collectors.toList());
        reportService = new ReportServiceImp();
    }

    @Test
    public void makeBalanceReport_All_Ok() {
        List<String> actual = reportService.makeBalanceReport(testInputList);
        List<String> expect = Stream.of(resultReport).collect(Collectors.toList());
        assertTrue(actual.containsAll(expect));
    }

    @Test
    public void makeBalanceReport_EmptyList_Ok() {
        List<String> actual = reportService.makeBalanceReport(new ArrayList<>());
        List<String> expect = Stream.of(emptyResultReport).collect(Collectors.toList());
        assertTrue(actual.containsAll(expect));
    }
}
