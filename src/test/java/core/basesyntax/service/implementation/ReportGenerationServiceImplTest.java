package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGenerationServiceImplTest {
    private ReportGenerationService reportGenerationService;

    @BeforeEach
    void setUp() {
        reportGenerationService = new ReportGenerationServiceImpl();
    }

    @Test
    void generate_validData_ok() {
        Storage.STORAGE.put("apricot", 15);
        String report = reportGenerationService.generate();
        String actualReport = "fruit,quantity" + System.lineSeparator()
                + "apricot,15" + System.lineSeparator();
        assertEquals(actualReport, report);
    }

    @Test
    void generate_emptyData_ok() {
        String report = reportGenerationService.generate();
        String actualReport = "fruit,quantity" + System.lineSeparator();
        assertEquals(actualReport, report);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}
