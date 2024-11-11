package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorServiceTest {

    private ReportGeneratorService reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void getReport_noTransactions_emptyReport() {
        String expected = "fruit,quantity\n";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_withTransactions_success() {
        ShopStorage storage = ShopStorage.getInstance();
        storage.setFruitQuantity("banana", 100);
        storage.setFruitQuantity("apple", 50);

        String expected = "fruit,quantity\nbanana,100\napple,50\n";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }
}
