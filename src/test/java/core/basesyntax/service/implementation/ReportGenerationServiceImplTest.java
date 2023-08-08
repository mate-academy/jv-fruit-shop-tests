package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGenerationServiceImplTest {
    private static String SEPARATOR = System.lineSeparator();
    private static ReportGenerationService reportGenerationService;

    @BeforeAll
    static void beforeAll() {
        reportGenerationService = new ReportGenerationServiceImpl();
    }

    @Test
    void generate_validData_ok() {
        Storage.STORAGE.put("apricot", 15);
        String report = reportGenerationService.generate();
        String actualReport = "fruit,quantity" + SEPARATOR
                + "apricot,15" + SEPARATOR;
        assertEquals(actualReport, report);
    }

    @Test
    void generate_negativeData_ok() {
        Storage.STORAGE.put("apricot", -15);
        assertThrows(RuntimeException.class, () -> reportGenerationService.generate());

    }

    @Test
    void generate_emptyData_ok() {
        String report = reportGenerationService.generate();
        String actualReport = "fruit,quantity" + SEPARATOR;
        assertEquals(actualReport, report);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}
