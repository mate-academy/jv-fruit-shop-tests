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
    private static final String CHERRY = "cherry";
    private static final int CHERRY_QUANTITY = 100;
    private static final int WRONG_CHERRY_QUANTITY = -100;
    private static final String DOPPELGANGER = "doppelganger";
    private static final int DOPPELGANGER_QUANTITY = 50;
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String COMMA = ",";
    private static final String SPECIAL_CHARACTER_APPLE = "app,le";
    private static final int SPECIAL_CHARACTER_APPLE_QUANTITY = 10;
    private static final String SPECIAL_CHARACTER_BANANA = "ba%nana";
    private static final int SPECIAL_CHARACTER_BANANA_QUANTITY = 20;
    private static final int HEADER_INDEX = 0;
    private static final int APPLE_REPORT_INDEX = 1;
    private static final int BANANA_REPORT_INDEX = 2;

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
        Storage.addFruits(CHERRY, CHERRY_QUANTITY);
        Storage.addFruits(DOPPELGANGER, DOPPELGANGER_QUANTITY);
        List<String> expected = new ArrayList<>();
        expected.add(REPORT_HEADER);
        expected.add(CHERRY + COMMA + CHERRY_QUANTITY);
        expected.add(DOPPELGANGER + COMMA + DOPPELGANGER_QUANTITY);
        List<String> report = reportService.generateReport();
        assertEquals(expected, report);
    }

    @Test
    void storageNegativeAddNotOkay() {
        assertThrows(FruitsQuantityException.class,
                () -> Storage.addFruits(CHERRY, WRONG_CHERRY_QUANTITY));
        List<String> report = reportService.generateReport();
        assertTrue(report.isEmpty());
    }

    @Test
    void specialCharactersOkay() {
        Storage.getStorage().put(SPECIAL_CHARACTER_APPLE, SPECIAL_CHARACTER_APPLE_QUANTITY);
        Storage.getStorage().put(SPECIAL_CHARACTER_BANANA, SPECIAL_CHARACTER_BANANA_QUANTITY);
        List<String> actualReport = reportService.generateReport();
        assertEquals(REPORT_HEADER, actualReport.get(HEADER_INDEX));
        assertEquals(SPECIAL_CHARACTER_APPLE + COMMA + SPECIAL_CHARACTER_APPLE_QUANTITY,
                actualReport.get(APPLE_REPORT_INDEX));
        assertEquals(SPECIAL_CHARACTER_BANANA + COMMA + SPECIAL_CHARACTER_BANANA_QUANTITY,
                actualReport.get(BANANA_REPORT_INDEX));
    }

    @AfterEach
    void onTearDown() {
        Storage.getStorage().clear();
    }
}
