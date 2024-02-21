package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest {
    private static final String REPORT_HEADER = "fruit,quantity";
    private StringBuilder builder;
    private ReportGeneratorService generator;

    @BeforeEach
    void setUp() {
        Storage.getFruitStorage().clear();
        builder = new StringBuilder();
        builder.append(REPORT_HEADER);
        generator = new ReportGeneratorServiceImpl();
    }

    @Test
    void generateReport_empty_Ok() {
        String actual = generator.generateReport();

        assertEquals(builder.toString(), actual);
    }

    @Test
    void generateReport_oneOperationOneFruit_Ok() {
        builder.append(System.lineSeparator())
                .append("grapefruit,999");

        Storage.getFruitStorage().put("grapefruit", 999);
        String actual = generator.generateReport();

        assertEquals(builder.toString(), actual);
    }

    @Test
    void generateReport_oneOperationMultipleFruits_Ok() {
        Storage.getFruitStorage().put("grapefruit", 999);
        Storage.getFruitStorage().put("apple", 890);
        Storage.getFruitStorage().put("banana", 100);
        Storage.getFruitStorage().put("blueberry", 1999);

        // Due to HashMap random indexing we need to map it using stream.
        builder.append(Storage.getFruitStorage().entrySet().stream()
                .map(e -> System.lineSeparator() + e.getKey() + "," + e.getValue())
                .collect(Collectors.joining()));

        String actual = generator.generateReport();

        assertEquals(builder.toString(), actual);
    }
}
