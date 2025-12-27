package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportWriteServiceImplTest {
    @Test
    void writeReport_valid_ok() {
        Map<String, Integer> report = new HashMap<>();
        report.put("apple", 6);
        report.put("banana", 5);
        ReportWriteServiceImpl reportWriteService = new ReportWriteServiceImpl();
        List<String> stringList = reportWriteService.writeReport(report);
        assertEquals(3,stringList.size());
        assertEquals("banana,5", stringList.get(1));
        assertEquals("fruit, quantity", stringList.get(0));
    }

    @Test
    void writeReport_EmptyMap_ok() {
        Map<String, Integer> report = new HashMap<>();
        ReportWriteServiceImpl reportWriteService = new ReportWriteServiceImpl();
        List<String> stringList = reportWriteService.writeReport(report);
        assertEquals(1, stringList.size());
        assertEquals("fruit, quantity", stringList.get(0));
    }

    @Test
    void writeReport_Null_notOk() {
        ReportWriteServiceImpl reportWriteService = new ReportWriteServiceImpl();
        assertThrows(NullPointerException.class, () -> reportWriteService.writeReport(null));
    }
}

