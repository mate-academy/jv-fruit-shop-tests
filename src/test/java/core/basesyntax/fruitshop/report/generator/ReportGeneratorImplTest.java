package core.basesyntax.fruitshop.report.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String TITLE = "fruit,quantity\n";
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @BeforeAll
    public static void clearStorage() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void generateReport_emptyStorage_ok() {
        clearStorage();
        String actual = reportGenerator.generateReport();
        assertEquals(TITLE, actual);
    }

    @Test
    void generateReport_filledStorage_ok() {
        clearStorage();
        Storage.getFruitStorage().put("paprika", 10);
        Storage.getFruitStorage().put("melon", 3);
        String actual = reportGenerator.generateReport();
        String builder = TITLE
                + "paprika" + "," + 10
                + System.lineSeparator()
                + "melon" + "," + 3
                + System.lineSeparator();
        assertEquals(builder, actual);
    }
}
