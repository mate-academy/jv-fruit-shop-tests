package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.implementations.ReportServiceImpl;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
    private static final String CHERRY = "cherry";
    private static final int CHERRY_QUANTITY = 100;
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

    private static ReportService reportService;

    @BeforeAll
    static void createStorage() {
        reportService = new ReportServiceImpl();
        Storage.createMap();
    }

    @BeforeEach
    void setUp() {
        Storage.clear();
    }

    @Test
    void generateReport_emptyStorage_okay() {
        String report = reportService.generateReport();
        assertTrue(report.isEmpty());
    }

    @Test
    void generateReport_filledStorage_okay() {
        Storage.addFruits(CHERRY, CHERRY_QUANTITY);
        Storage.addFruits(DOPPELGANGER, DOPPELGANGER_QUANTITY);
        String expectedReport = REPORT_HEADER + System.lineSeparator()
                + CHERRY + COMMA + CHERRY_QUANTITY + System.lineSeparator()
                + DOPPELGANGER + COMMA + DOPPELGANGER_QUANTITY;
        String report = reportService.generateReport();
        assertEquals(expectedReport, report);
    }

    @Test
    void generateReport_specialCharsInFruitName_okay() {
        Storage.addFruits(SPECIAL_CHARACTER_APPLE, SPECIAL_CHARACTER_APPLE_QUANTITY);
        Storage.addFruits(SPECIAL_CHARACTER_APPLE, SPECIAL_CHARACTER_APPLE_QUANTITY);
        Storage.addFruits(SPECIAL_CHARACTER_BANANA, SPECIAL_CHARACTER_BANANA_QUANTITY);
        String actualReport = reportService.generateReport();
        String[] separatedLinesReport = actualReport.split(System.lineSeparator());
        assertEquals(REPORT_HEADER, separatedLinesReport[HEADER_INDEX]);
        assertEquals(SPECIAL_CHARACTER_APPLE + COMMA + SPECIAL_CHARACTER_APPLE_QUANTITY,
                separatedLinesReport[APPLE_REPORT_INDEX]);
        assertEquals(SPECIAL_CHARACTER_BANANA + COMMA + SPECIAL_CHARACTER_BANANA_QUANTITY,
                separatedLinesReport[BANANA_REPORT_INDEX]);
    }
}
