package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.service.ReportService;
import core.basesyntax.storage.FruitStorage;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class ReportServiceImplTest {
    private static final ReportService reportService = new ReportServiceImpl();

    @BeforeEach
    public void cleanStorage() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void createReportTest_OK() {
        FruitStorage.fruitStorage.put("banana", 150);
        FruitStorage.fruitStorage.put("apple", 100);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,150" + System.lineSeparator()
                + "apple,100";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReportTest_NullKey_notOK() {
        FruitStorage.fruitStorage.put(null, 150);
        FruitStorage.fruitStorage.put(null, 100);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,150" + System.lineSeparator()
                + "apple,100";
        String actual = reportService.createReport();
        assertNotEquals(expected, actual);
    }

    @Test
    public void createReportTest_NullValue_notOK() {
        FruitStorage.fruitStorage.put("banana", null);
        FruitStorage.fruitStorage.put("apple", null);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,150" + System.lineSeparator()
                + "apple,100";
        String actual = reportService.createReport();
        assertNotEquals(expected, actual);
    }

    @Test
    public void createReportTest_NullValueAndKey_notOK() {
        FruitStorage.fruitStorage.put(null, null);
        FruitStorage.fruitStorage.put(null, null);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,150" + System.lineSeparator()
                + "apple,100";
        String actual = reportService.createReport();
        assertNotEquals(expected, actual);
    }
}
