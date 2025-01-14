package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String lineSeparator = System.lineSeparator();

    private static Storage storage;
    private static ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void getReport_storageIsValid_Ok() {
        storage.set("banana", 20);
        storage.set("apple", 120);
        storage.add("banana", 100);
        storage.add("apple", 10);
        storage.remove("banana", 5);

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("fruit,quantity").append(lineSeparator);
        resultBuilder.append("banana,115").append(lineSeparator);
        resultBuilder.append("apple,130").append(lineSeparator);
        String expectedResult = resultBuilder.toString();

        String actualResult = reportGenerator.getReport(storage);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void getReport_storageIsNullOrEmpty_Ok() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reportGenerator.getReport(storage);
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "The storage is null or empty";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
