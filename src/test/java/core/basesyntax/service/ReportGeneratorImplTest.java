package core.basesyntax.service;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.fruits = new LinkedHashMap<>();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();

        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void getReport_twoFruits_ok() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 15);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();

        assertEquals(expected, reportGenerator.getReport());
    }
}
