package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import database.Storage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

class ReportGeneratorTest {
    private static final String TITLE = "fruit,quantity";
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 15;
    private static final String BANANA = "banana";
    private static final int BANANA_QUANTITY = 36;
    private static final String ORANGE = "orange";
    private static final int ORANGE_QUANTITY = 19;
    private static ReportGenerator generator;

    @BeforeAll
    static void beforeAll() {
        generator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.clearStorage();
    }

    @Test
    void getReport_ok() {
        Storage.addToStorage(APPLE, APPLE_QUANTITY);
        Storage.addToStorage(BANANA, BANANA_QUANTITY);
        Storage.addToStorage(ORANGE, ORANGE_QUANTITY);
        String actualReport = generator.getReport();
        Set<String> actualSet = new HashSet<>(
                Arrays.asList(actualReport.split(System.lineSeparator())));
        Set<String> expectedSet = new HashSet<>(
                Arrays.asList(TITLE, "apple,15", "banana,36", "orange,19"));
        assertEquals(expectedSet, actualSet);
    }

    @Test
    void getReport_emptyStorage_notOk() {
        assertThrows(RuntimeException.class, () -> generator.getReport());
    }
}
