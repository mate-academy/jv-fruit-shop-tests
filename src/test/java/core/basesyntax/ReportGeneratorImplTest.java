package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.ReportGeneratorImpl;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.getFruitStorage().clear();
    }

    @Test
    void getReport_nonEmptyStorage_ok() {
        Storage.getFruitStorage().put("apple", 50);
        Storage.getFruitStorage().put("banana", 30);
        Storage.getFruitStorage().put("cherry", 20);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "cherry,20";
        assertEquals(expectedReport, reportGenerator.getReport());
    }

    @Test
    void getReport_alphabeticalOrder_ok() {
        Storage.getFruitStorage().put("banana", 30);
        Storage.getFruitStorage().put("apple", 50);
        Storage.getFruitStorage().put("cherry", 20);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "cherry,20";
        assertEquals(expectedReport, reportGenerator.getReport());
    }
}
