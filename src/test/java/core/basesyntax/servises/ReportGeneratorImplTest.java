package core.basesyntax.servises;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.report.ReportGenerator;
import core.basesyntax.report.ReportGeneratorImpl;
import core.basesyntax.storage.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String FILE_HEADER = "fruit,quantity";
    private ReportGenerator reportGenerator;
    private StringBuilder builder;
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        builder = new StringBuilder();
        fruitStorage = new FruitStorage();
    }

    @Test
    void getReport_validReport_Ok() {
        fruitStorage.saveItem("banana", 0);
        fruitStorage.saveItem("apple", 100);
        String expected = builder.append(FILE_HEADER).append(System.lineSeparator())
                .append("banana,0").append(System.lineSeparator())
                .append("apple,100").append(System.lineSeparator()).toString();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyReport_Ok() {
        String expected = builder.append(FILE_HEADER).append(System.lineSeparator()).toString();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    void afterVoid() {
        fruitStorage.clear();
    }
}
