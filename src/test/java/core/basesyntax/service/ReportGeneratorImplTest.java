package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static ReportGeneratorService reportGeneratorService;
    private static final String TITLE = "fruit,quantity\n";

    @BeforeAll
    static void beforeAll() {
        reportGeneratorService = new ReportGeneratorImpl();
    }

    @AfterEach
    void cleanStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void reportGenerator_emptyStorage_IsOk() {
        String actual = reportGeneratorService.generateReport();
        Assertions.assertEquals(TITLE, actual);
    }

    @Test
    public void reportGenerator_correct_Ok() {
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 200);
        String expected = TITLE
                + "banana,100" + System.lineSeparator()
                + "apple,200" + System.lineSeparator();
        String actual = reportGeneratorService.generateReport();
        Assertions.assertEquals(expected, actual);
    }
}

