package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String lineSeparator = System.lineSeparator();

    private static Storage storage;
    private static String expectedResult;
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        storage.set("banana", 20);
        storage.set("apple", 120);
        storage.add("banana", 100);
        storage.add("apple", 10);
        storage.remove("banana", 5);

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("fruit,quantity").append(lineSeparator);
        resultBuilder.append("banana,115").append(lineSeparator);
        resultBuilder.append("apple,130").append(lineSeparator);

        expectedResult = resultBuilder.toString();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void getReport_Ok() {
        String actualResult = reportGenerator.getReport(storage);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
