package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.ReportCreatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportCreatorServiceTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void readerService_validReport_ok() {
        String expected =
                String.format(
                        "fruit,quantity%n"
                        + "banana,30%n"
                        + "apple,348"
                );

        HashMap<String, Integer> validData = new HashMap<>(Map.of(
                "apple", 348,
                "banana", 30

        ));

        FruitStorage.storage.putAll(validData);
        String actual = reportCreator.createReport();

        assertEquals(expected, actual);
    }

    @Test
    void readerService_empty_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportCreator.createReport();

        assertEquals(expected, actual);
    }
}
