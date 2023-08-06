package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ReportGeneratorException;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
        putInStorage("apple", 100);
        putInStorage("banana",20);
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR
                + "apple,100";
        String actual = reportGenerator.generateReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void generateReport_emptyStorage_notOk() {
        Storage.getStorage().clear();
        ReportGeneratorException exception = null;
        try {
            reportGenerator.generateReport();
        } catch (ReportGeneratorException e) {
            exception = e;
        }
        String expected = "Can't create report, because there aren't any fruit in the Storage";
        Assertions.assertEquals(expected, exception.getMessage());
    }

    private void putInStorage(String key, Integer value) {
        Storage.getStorage().put(key, value);
    }
}
