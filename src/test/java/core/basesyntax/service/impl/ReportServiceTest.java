package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
    @Test
    void getReport_fromStorage_ok() {
        List<String> expected = List.of("fruit,quantity", "orange,140", "lemon,67");
        Storage.fruits.put("orange", 140);
        Storage.fruits.put("lemon", 67);
        ReportService reportService = new ReportServiceImpl();
        List<String> actual = reportService.getReport();
        assertLinesMatch(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual.toString());
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
