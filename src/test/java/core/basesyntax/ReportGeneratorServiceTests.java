package core.basesyntax;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.impl.ReportGeneratorServiceImpl;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorServiceTests {
    private String valueOutputFile;
    private FruitShopStorage fruitShopStorageDefault;

    @BeforeEach
    void setUp() {
        fruitShopStorageDefault = new FruitShopStorage();
        fruitShopStorageDefault.put("banana", 100);
        fruitShopStorageDefault.put("apple", 100);
        valueOutputFile = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100";
    }

    @Test
    void generateReport_generateReportFromStorage_ok() {
        ReportGeneratorService reportGeneratorService = new ReportGeneratorServiceImpl();
        String actual = reportGeneratorService.generateReport(fruitShopStorageDefault);
        String expected = valueOutputFile;
        String message = "Expected report: " + expected + ", but got: " + actual;
        Assertions.assertEquals(actual, expected, message);
    }

    @AfterEach
    public void afterEachTest() {
        fruitShopStorageDefault.clear();
    }
}
