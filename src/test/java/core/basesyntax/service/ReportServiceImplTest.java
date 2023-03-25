package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportServiceImpl reportService;
    private String expectedText;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        expectedText = "banana,154";
        Storage.fruits.put("banana", 154);
    }

    @Test
    public void reportService_readFromStorage_Ok() {
        String text = reportService.createReport();
        String[] actualResult = text.split(System.lineSeparator());
        Assert.assertEquals("Test failed! You should returned next text "
                + expectedText + " but you returned "
                + actualResult[0], actualResult[0], expectedText);
    }
}
