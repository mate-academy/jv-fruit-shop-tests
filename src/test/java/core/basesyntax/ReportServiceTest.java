package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.FruitsQuantityException;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.implementations.ReportServiceImpl;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        Storage.createMap();
    }

    @Test
    void emptyStorageReportOkay() {
        Storage.getStorage().clear();
        List<String> report = reportService.generateReport();
        assertTrue(report.isEmpty());
    }

    @Test
    void storageReportOkay() {
        Storage.addFruits("cherry", 100);
        Storage.addFruits("doppelganger", 50);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("cherry,100");
        expected.add("doppelganger,50");
        List<String> report = reportService.generateReport();
        assertEquals(expected, report);
    }

    @Test
    void storageNegativeAddNotOkay() {
        assertThrows(FruitsQuantityException.class, () -> Storage.addFruits("cherry", -100));
    }

    @Test
    void specialCharactersOkay() {
        Storage.getStorage().put("app,le", 10);
        Storage.getStorage().put("ba%nana", 20);
        List<String> actualReport = reportService.generateReport();
        assertEquals("fruit,quantity", actualReport.get(0));
        assertEquals("app,le,10", actualReport.get(1));
        assertEquals("ba%nana,20", actualReport.get(2));
    }

    @AfterEach
    void onTearDown() {
        Storage.getStorage().clear();
    }
}
