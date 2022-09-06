package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static ReportGeneratorService reportGeneratorService;

    @BeforeClass
    public static void setUp() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Test
    public void generate_header_ok() {
        List<String> actual = reportGeneratorService.generate();
        assertEquals("Expected size must be 1 but is " + actual.size(), 1, actual.size());
        assertEquals("Header not found", "fruit,quantity", actual.get(0));
    }

    @Test
    public void generate_list_ok() {
        Storage.fruits.put("watermelon", 99);
        List<String> actual = reportGeneratorService.generate();
        assertEquals("Expected size must be 2 but is " + actual.size(), 2, actual.size());
        assertEquals("One fruit not found", "watermelon,99", actual.get(1));
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
