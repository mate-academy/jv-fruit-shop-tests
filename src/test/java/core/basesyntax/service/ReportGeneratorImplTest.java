package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.ReportGenerator;
import core.basesyntax.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
        storage = new Storage();
    }

    @Test
    void getReport_Ok() {
        StringBuilder builder = new StringBuilder("fruit, quantity:");
        builder.append(System.lineSeparator())
                .append("banana, 5")
                .append(System.lineSeparator())
                .append("apple, 5")
                .append(System.lineSeparator());
        storage.addFruit("apple", 5);
        storage.addFruit("banana", 5);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(builder.toString(), reportGenerator.getReport(storage));
    }
}
