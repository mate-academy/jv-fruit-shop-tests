package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private ReportServiceImpl reportService;
    private String expectedText;

    @BeforeEach
    public void setUp() {
        reportService = new ReportServiceImpl();
        expectedText = "banana,154";
        Storage.fruits.put("banana", 154);
    }

    @Test
    public void createReport_rightStorage_Ok() {
        String text = reportService.createReport();
        String[] actualResult = text.split(System.lineSeparator());
        Assertions.assertEquals(actualResult[0], expectedText);
    }
}
