package fruit.shop.service.impl;

import fruit.shop.db.Storage;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class ReportGeneratorImplTest {
    private static final String TITLE = "fruit,quantity\n";

    @AfterEach
    void cleanStorage() {
        Storage.FRUITS.clear();
    }

    @Test
    void generateReport_emptyMap_Ok() {
        assertEquals(TITLE, new ReportGeneratorImpl().generateReport());
    }

    @Test
    void generateReport_correct_Ok() {
        Storage.FRUITS.put("banana", 150);
        Storage.FRUITS.put("apple", 70);
        Storage.FRUITS.put("melon", 30);
        String expected = TITLE +
                "banana,150" + System.lineSeparator() +
                "apple,70" + System.lineSeparator() +
                "melon,30" + System.lineSeparator();
        assertEquals(expected, new ReportGeneratorImpl().generateReport());
    }
}