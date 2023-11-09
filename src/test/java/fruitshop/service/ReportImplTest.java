package fruitshop.service;

import fruitshop.db.Storage;
import fruitshop.service.impl.ReportImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportImplTest {
    private Report report;

    @BeforeAll
    void setUp() {
        report = new ReportImpl();
    }

    @Test
    void create_ReturnsRightReport_Ok() {
        String expected = "fruit,quantity"
                    + System.lineSeparator()
                    + "banana,20"
                    + System.lineSeparator()
                    + "apple,100"
                    + System.lineSeparator();

        Storage.setAmount("banana", 20);
        Storage.setAmount("apple", 100);
        String result = report.create();

        Assertions.assertEquals(result, expected, "Generated report is incorrect.");
    }
}
