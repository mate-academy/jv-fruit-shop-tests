package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.ReportGeneratorService;

public class ReportGeneratorServiceImplTest {
    private static final String EXPECTED_REPORT =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,100" + System.lineSeparator()
                    + "apple,100" + System.lineSeparator();
    private ReportGeneratorService reportGeneratorService;

    @Before
    public void setUp() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 100);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void createMessage_correctData_Ok() {
        String actual = reportGeneratorService.createMessage();
        assertEquals(EXPECTED_REPORT, actual);
    }
}
