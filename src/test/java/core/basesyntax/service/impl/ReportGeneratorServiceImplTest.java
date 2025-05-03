package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ReportGeneratorException;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final ReportGeneratorService reportGenerator = new ReportGeneratorServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void generateReport_validData_ok() {
        Storage.getStorage().put("apple", 100);
        Storage.getStorage().put("banana",20);

        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR
                + "apple,100";
        String actual = reportGenerator.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    void generateReport_emptyStorage_notOk() {
        Storage.getStorage().clear();
        ReportGeneratorException exception = assertThrows(ReportGeneratorException.class,
                reportGenerator::generateReport);

        String expected = "Can't create report, because there aren't any fruit in the Storage";

        assertEquals(expected, exception.getMessage());
    }
}
