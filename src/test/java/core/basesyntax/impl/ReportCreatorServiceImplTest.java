package core.basesyntax.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
    private ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();

    @Test
    void createReport_nullMap_notOk() {
        assertThrows(RuntimeException.class, () -> reportCreatorService.createReport(null));
    }

    @Test
    void createReport_emptyMap_notOk() {
        Map<String, Integer> map = new HashMap<>();
        String report = reportCreatorService.createReport(map);
        int expectedLength = 15;
        assertSame(expectedLength, report.length());
    }

    @Test
    void createReport_validData_ok() {
        Map<String, Integer> map = Map.of("apple", 1);
        String report = reportCreatorService.createReport(map);
        String expectedString = "apple,1";
        assertTrue(report.contains(expectedString));
    }
}
