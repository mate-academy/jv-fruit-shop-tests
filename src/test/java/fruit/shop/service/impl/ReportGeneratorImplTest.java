package fruit.shop.service.impl;

import static org.junit.Assert.assertEquals;

import fruit.shop.db.Storage;
import fruit.shop.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static final String TITLE = "fruit,quantity\n";

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void cleanStorage() {
        Storage.FRUITS.clear();
    }

    @Test
    void generateReport_emptyMap_Ok() {
        assertEquals(TITLE, reportGenerator.generateReport());
    }

    @Test
    void generateReport_correct_Ok() {
        Storage.FRUITS.put("banana", 150);
        Storage.FRUITS.put("apple", 70);
        Storage.FRUITS.put("melon", 30);
        String expected = TITLE
                + "banana,150" + System.lineSeparator()
                + "apple,70" + System.lineSeparator()
                + "melon,30" + System.lineSeparator();
        assertEquals(expected, reportGenerator.generateReport());
    }
}
